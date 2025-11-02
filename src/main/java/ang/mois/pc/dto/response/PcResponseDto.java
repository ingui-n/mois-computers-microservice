package ang.mois.pc.dto.response;
import java.time.LocalDateTime;

public record PcResponseDto(
        Long id,
        String name,
        boolean available,
        Long computerRoomId,
        Long configId,
        LocalDateTime createdAt
) {}
