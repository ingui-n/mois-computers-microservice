package ang.mois.pc.dto;

import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;

public record PcFullDocument(
        String id,
        String status,
        PcType type,
        Room room) {
}