package ang.mois.pc.repository;

import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PcRepository extends JpaRepository<Pc, Long> {
    List<Pc> findByStatus(String status);
    List<Pc> findByRoom(Room room);
    List<Pc> findAllByRoomIn(List<Room> room);
    List<Pc> findByPcType(PcType type);
}
