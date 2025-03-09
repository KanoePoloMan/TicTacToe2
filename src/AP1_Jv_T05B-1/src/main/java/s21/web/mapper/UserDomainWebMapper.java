package s21.web.mapper;

import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.User;
import s21.web.model.UserDTO;

@Mapper
public interface UserDomainWebMapper {
    UserDomainWebMapper INSTANCE = Mappers.getMapper(UserDomainWebMapper.class);

    default UserDTO domainToWeb(User domain) {
        if(domain == null) return null;
        return new UserDTO(domain.getUuid(), 
                           domain.getUsername(), 
                           domain.getPassword(),
                           domain.getAuthorities().stream().collect(Collectors.toSet()));
    }
}
