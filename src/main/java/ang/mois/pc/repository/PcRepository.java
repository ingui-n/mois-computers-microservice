package ang.mois.pc.repository;

import ang.mois.pc.entity.Pc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PcRepository extends MongoRepository<Pc, String> {
    List<Pc> findByStatus(String status);
    List<Pc> findByRoomId(String roomId);
    List<Pc> findByTypeId(String typeId);
}
