package ang.mois.pc.dto;

import org.springframework.lang.Nullable;

public record UpdatePcDto(
        @Nullable String name,
        @Nullable boolean available,
        @Nullable Long computerRoomId,
        @Nullable Long configId
) {
}
