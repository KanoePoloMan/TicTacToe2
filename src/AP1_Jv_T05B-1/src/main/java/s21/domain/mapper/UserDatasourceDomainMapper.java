package s21.domain.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.datasource.model.UserDAO;
import s21.domain.model.Role;
import s21.domain.model.User;

@Mapper
public interface UserDatasourceDomainMapper {
    UserDatasourceDomainMapper INSTANCE = Mappers.getMapper(UserDatasourceDomainMapper.class);

    default User datasourceToDomain(UserDAO datasource) {
        if(datasource == null) return null;
        return new User(datasource.getRoles().stream().map(Role::valueOf).collect(Collectors.toSet()), 
                        datasource.getUuid() , 
                        datasource.getLogin(), 
                        datasource.getPassword()
                    );
    }
    default List<User> datasourceToDomainList(List<UserDAO> datasource) {
        return datasource.stream().map(this::datasourceToDomain).toList();
    }
}
