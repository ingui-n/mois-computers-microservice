package ang.mois.pc.mapper;
import ang.mois.pc.dto.request.PcRequestDto;
import ang.mois.pc.entity.Pc;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PcMapper {

    // --- to Entity: copy all fields ---
    @Mapping(target = "id", ignore = true) // don’t copy from DTO
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    Pc toEntity(PcRequestDto dto);

    // --- to Dto ---
    PcRequestDto toDto(Pc entity);

    List<PcRequestDto> toDtoList(List<Pc> entities);

    // --- Update: merge non-null fields ---
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PcRequestDto dto, @MappingTarget Pc entity);
}