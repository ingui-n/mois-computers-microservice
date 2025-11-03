package ang.mois.pc.dto.response;

import java.time.LocalDateTime;

public record RoomResponseDto(
        Long id,
        Long facultyId,
        String name,
        LocalDateTime createdAt
) {}

