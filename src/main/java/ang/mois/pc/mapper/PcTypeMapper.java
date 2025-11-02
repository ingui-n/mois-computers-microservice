package ang.mois.pc.mapper;

import ang.mois.pc.dto.request.PcTypeRequestDto;
import ang.mois.pc.dto.response.PcTypeResponseDto;
import ang.mois.pc.entity.PcType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PcTypeMapper {
    // Requests
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    PcType toEntity(PcTypeRequestDto dto);

    // Responses
    PcTypeResponseDto toResponseDto(PcType entity);
    List<PcTypeResponseDto> toResponseDtoList(List<PcType> entities);
}
