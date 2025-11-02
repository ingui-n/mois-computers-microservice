package ang.mois.pc.mapper;

import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.dto.response.FacultyResponseDto;
import ang.mois.pc.entity.Faculty;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FacultyMapper {

    // Requests
    @Mapping(target = "id", ignore = true) // don’t copy from DTO
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    Faculty toEntity(FacultyRequestDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(FacultyRequestDto dto, @MappingTarget Faculty entity);

    // Responses
    FacultyResponseDto toResponseDto(Faculty entity);
    List<FacultyResponseDto> toResponseDtoList(List<Faculty> entities);
}
