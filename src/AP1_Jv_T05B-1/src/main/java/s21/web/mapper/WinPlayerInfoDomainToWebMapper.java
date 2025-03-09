package s21.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import s21.domain.model.WinPlayerInfo;
import s21.web.model.WinPlayerInfoDTO;

@Mapper
public interface WinPlayerInfoDomainToWebMapper {
    WinPlayerInfoDomainToWebMapper INSTANCE = Mappers.getMapper(WinPlayerInfoDomainToWebMapper.class);

    default WinPlayerInfoDTO domainToWeb(WinPlayerInfo domain) {
        if(domain == null) return null;
        return new WinPlayerInfoDTO(domain.uuid().toString(), 
                                    domain.login(), 
                                    String.valueOf(domain.ratio()));
    }
    default List<WinPlayerInfoDTO> domainToWeb(List<WinPlayerInfo> domain) {
        if(domain == null) return null;
        return domain.stream().map(this::domainToWeb).toList();
    }
}
