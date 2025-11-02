package ang.mois.pc.mapper;

import ang.mois.pc.dto.request.PcTypeRequestDto;
import ang.mois.pc.dto.response.PcTypeResponseDto;
import ang.mois.pc.entity.PcType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PcTypeMapper {
    // Requests
    PcType toEntity(PcTypeRequestDto dto);
    PcTypeRequestDto toRequestDto(PcType entity);
    List<PcTypeRequestDto> toRequestDtoList(List<PcType> entities);

    // Responses
    PcTypeResponseDto toResponseDto(PcType entity);
    List<PcTypeResponseDto> toResponseDtoList(List<PcType> entities);
}
