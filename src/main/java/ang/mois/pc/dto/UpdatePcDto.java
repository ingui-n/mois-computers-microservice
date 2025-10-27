package ang.mois.pc.dto;

import org.springframework.lang.Nullable;

public record UpdatePcDto(
        @Nullable String name,
        @Nullable Boolean available,
        @Nullable Long computerRoomId,
        @Nullable Long configId
) {
}
