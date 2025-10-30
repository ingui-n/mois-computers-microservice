package ang.mois.pc.dto;

import ang.mois.pc.validation.ValidationGroups;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.sql.Time;

public record FacultyDto(

        // Always validate: if 'name' is present, it must not be blank and have 2-100 chars.
        @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
        // Only on create: 'name' is mandatory and cannot be blank.
        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Name is mandatory")
        String name,

        @Size(min = 1, max = 10, message = "Shortcut must be between 1 and 10 characters")
        @NotBlank(groups = ValidationGroups.OnCreate.class, message = "Shortcut is mandatory")
        String shortcut,

        @NotNull(groups = ValidationGroups.OnCreate.class, message = "Reservation start time is mandatory")
        Time reservationTimeStart,

        @NotNull(groups = ValidationGroups.OnCreate.class, message = "Reservation end time is mandatory")
        Time reservationTimeEnd,

        // Always validate: if this number is provided, it must be 0 or more
        @Min(value = 0, message = "Reservation count must be zero or positive")
        // Only on create: the field itself is mandatory
        @NotNull(groups = ValidationGroups.OnCreate.class, message = "Max user reservation count is mandatory")
        Integer maxUserReservationCount,

        @Min(value = 0, message = "Reservation time must be zero or positive")
        @NotNull(groups = ValidationGroups.OnCreate.class, message = "Max user reservation time is mandatory")
        Integer maxUserReservationTime,

        @Min(value = 0, message = "Weekly reservation time must be zero or positive")
        @NotNull(groups = ValidationGroups.OnCreate.class, message = "Max user reservation time weekly is mandatory")
        Integer maxUserReservationTimeWeekly
) {
}
