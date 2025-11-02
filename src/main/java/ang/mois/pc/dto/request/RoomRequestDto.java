package ang.mois.pc.dto.request;

import ang.mois.pc.validation.ValidationGroups;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RoomRequestDto(

        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Name is mandatory")
        String name,

        @NotNull(groups = ValidationGroups.OnCreate.class, message = "Faculty Id is mandatory")
        Long facultyId) {
}
