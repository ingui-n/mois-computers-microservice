package ang.mois.pc.dto;

import org.springframework.lang.NonNull;

public record CreateRoomDto(@NonNull String name, @NonNull Long facultyId) {
}
