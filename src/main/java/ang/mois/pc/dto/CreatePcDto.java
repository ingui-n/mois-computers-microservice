package ang.mois.pc.dto;

import org.springframework.lang.NonNull;

public record CreatePcDto(
        @NonNull String name,
        @NonNull boolean available,
        @NonNull Long computerRoomId,
        @NonNull Long configId
) {
}
