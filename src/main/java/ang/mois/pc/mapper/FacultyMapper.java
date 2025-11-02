package ang.mois.pc.mapper;

import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.dto.request.PcRequestDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Pc;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    @Mapping(target = "id", ignore = true) // don’t copy from DTO
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    Faculty toEntity(FacultyRequestDto dto);

    FacultyRequestDto toDto(Faculty entity);

    List<PcRequestDto> toDtoList(List<Pc> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FacultyRequestDto dto, @MappingTarget Faculty entity);
}
