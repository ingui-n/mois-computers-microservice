package ang.mois.pc.dto;

import jakarta.annotation.Nullable;

public record FacultyDto(
        @Nullable String name,
        @Nullable String shortcut) {
}
