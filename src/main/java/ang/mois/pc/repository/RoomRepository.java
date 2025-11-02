package ang.mois.pc.repository;

import ang.mois.pc.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByFacultyId(Long facultyId);

    boolean existsByFacultyId(Long facultyId);
}
