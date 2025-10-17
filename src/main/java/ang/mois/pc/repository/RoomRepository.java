package ang.mois.pc.repository;

import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByFaculty(Faculty faculty);
}
