package ang.mois.pc.mapper;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.dto.PcDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Pc;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    @Mapping(target = "id", ignore = true) // don’t copy from DTO
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    Faculty toEntity(FacultyDto dto);

    FacultyDto toDto(Faculty entity);

    List<PcDto> toDtoList(List<Pc> entities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FacultyDto dto, @MappingTarget Faculty entity);
}
