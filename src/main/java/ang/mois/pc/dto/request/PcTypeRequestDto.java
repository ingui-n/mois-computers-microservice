package ang.mois.pc.dto.request;

import ang.mois.pc.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PcTypeRequestDto(

        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Name is mandatory")
        String name,

        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Cpu is mandatory")
        String cpu,

        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Ram is mandatory")
        String ram,

        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Gpu is mandatory")
        String gpu
){
}
