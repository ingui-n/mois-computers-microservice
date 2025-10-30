package ang.mois.pc.mapper;
import ang.mois.pc.dto.PcDto;
import ang.mois.pc.entity.Pc;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PcMapper {

    // --- to Entity: copy all fields ---
    @Mapping(target = "id", ignore = true) // don’t copy from DTO
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // set automatically
    Pc toEntity(PcDto dto);

    // --- to Dto ---
    PcDto toDto(Pc entity);

    List<PcDto> toDtoList(List<Pc> entities);

    // --- Update: merge non-null fields ---
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(PcDto dto, @MappingTarget Pc entity);
}