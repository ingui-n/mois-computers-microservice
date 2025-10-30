package ang.mois.pc.mapper;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.entity.Faculty;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // 1. Skip the ID and Rooms (optional but good practice)
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true); // Ignore null properties in DTOs

        // 2. Define custom mapping for fields that don't match or need special handling
        // Since 'createdAt' is not in the DTO, we create a specific TypeMap to set it.
        modelMapper.createTypeMap(FacultyDto.class, Faculty.class)
                .addMappings(mapper -> {
                    // Ignore fields that are not in the DTO or are managed by JPA/DB
                    mapper.skip(Faculty::setId);
                    mapper.skip(Faculty::setRooms);

                    // Use a custom converter or expression to set 'createdAt'
                    // This is one way to handle the LocalDateTime.now() requirement
                    mapper.map(src -> java.time.LocalDateTime.now(), Faculty::setCreatedAt);
                });

        return modelMapper;
    }
}
