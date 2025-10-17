package ang.mois.pc.repository;

import ang.mois.pc.entity.PcType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PcTypeRepository extends JpaRepository<PcType, Long> {
}