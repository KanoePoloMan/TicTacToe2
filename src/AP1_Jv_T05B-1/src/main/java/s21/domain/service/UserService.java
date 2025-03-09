package s21.domain.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import s21.datasource.mapper.UserDomainDatasourceMapper;
import s21.datasource.model.UserDAO;
import s21.datasource.repository.UserRepository;
import s21.domain.mapper.UserDatasourceDomainMapper;
import s21.domain.model.User;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private final UserDatasourceDomainMapper toDomainMapper = UserDatasourceDomainMapper.INSTANCE;
    private final UserDomainDatasourceMapper toDatasourceMapper = UserDomainDatasourceMapper.INSTANCE;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDAO = userRepository.findByLogin(username).orElse(null);
        System.out.println("UserDAO: " + userDAO + " " + username);
        if(userDAO == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }
        return toDomainMapper.datasourceToDomain(userDAO);
    }
    public List<User> getAll() {
        return toDomainMapper.datasourceToDomainList((List<UserDAO>)userRepository.findAll());
    }
    public void saveUser(User user) {
        userRepository.save(toDatasourceMapper.domainToDatasource(user));
    }
    public User getUserByUUID(UUID uuid) {
        return toDomainMapper.datasourceToDomain(userRepository.findById(uuid).orElse(null));
    }
}
