package s21.datasource.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.model.UserDAO;
import s21.domain.model.Role;
import s21.domain.model.User;

@Mapper
public interface UserDomainDatasourceMapper {
    UserDomainDatasourceMapper INSTANCE = Mappers.getMapper(UserDomainDatasourceMapper.class);

    default UserDAO domainToDatasource(User domain) {
        return new UserDAO(
                        domain.getUuid(), 
                        domain.getUsername(), 
                        domain.getPassword(),
                        domain.getAuthorities().stream().map(Role::name).collect(Collectors.toSet())
                    );
    }
    
    default List<UserDAO> domainToDatasourceList(List<User> domain) {
        return domain.stream().map(this::domainToDatasource).toList();
    }
}
