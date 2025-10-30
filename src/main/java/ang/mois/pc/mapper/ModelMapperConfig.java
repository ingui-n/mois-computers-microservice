package ang.mois.pc.mapper;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.dto.PcDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Pc;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Ignore null properties in DTOs
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true);

        // Custom mapping for fields that don't match or need special handling
        modelMapper.createTypeMap(FacultyDto.class, Faculty.class)
                .addMappings(mapper -> {
                    // skip special DB/JPA properties
                    mapper.skip(Faculty::setId);
                    mapper.skip(Faculty::setRooms);

                    // Use a custom converter or expression to set 'createdAt'
                    // This is one way to handle the LocalDateTime.now() requirement
                    mapper.map(src -> java.time.LocalDateTime.now(), Faculty::setCreatedAt);
                });

        modelMapper.createTypeMap(PcDto.class, Pc.class)
                .addMappings(mapper -> {
                    // skip special DB properties
                    mapper.skip(Pc::setId);
                    // skip the FK entities as we need more flexible handling for them in code
                    mapper.skip(Pc::setRoom);
                    mapper.skip(Pc::setPcType);
                    mapper.map(src -> java.time.LocalDateTime.now(), Pc::setCreatedAt);
                });

        return modelMapper;
    }
}
