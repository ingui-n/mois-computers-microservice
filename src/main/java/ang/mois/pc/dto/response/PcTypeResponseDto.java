package ang.mois.pc.dto.response;

import java.time.LocalDateTime;

public record PcTypeResponseDto(
        Long id,
        String name,
        String cpu,
        String ram,
        String gpu,
        LocalDateTime createdAt
) {}
