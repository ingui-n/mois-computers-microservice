package ang.mois.pc.repository;

import ang.mois.pc.entity.PcType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcTypeRepository extends MongoRepository<PcType, String> {
}
