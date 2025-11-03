package ang.mois.pc.dto.response;

import java.time.LocalDateTime;

public record PcUnwrappedResponseDto(
        Long id,
        String name,
        boolean available,
        RoomResponseDto computerRoom,
        PcTypeResponseDto computerConfig,
        LocalDateTime createdAt
) {
}
