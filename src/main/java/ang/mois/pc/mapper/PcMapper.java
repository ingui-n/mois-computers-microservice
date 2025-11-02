package ang.mois.pc.mapper;
import ang.mois.pc.dto.request.PcRequestDto;
import ang.mois.pc.dto.response.PcResponseDto;
import ang.mois.pc.entity.Pc;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PcMapper {

    // Request: to entity - copy all fields
    @Mapping(target = "id", ignore = true) // don’t copy from DTO
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    Pc toEntity(PcRequestDto dto);

    // Request: to Dto
    PcRequestDto toDto(Pc entity);
    List<PcRequestDto> toDtoList(List<Pc> entities);

    // Update: merge non-null fields
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PcRequestDto dto, @MappingTarget Pc entity);


    // Response: Entity → Response DTO
    @Mapping(target = "computerRoomId", source = "room.id")
    @Mapping(target = "configId", source = "pcType.id")
    PcResponseDto toResponseDto(Pc entity);

    List<PcResponseDto> toResponseDtoList(List<Pc> entities);
}