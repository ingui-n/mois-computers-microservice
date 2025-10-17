package ang.mois.pc.service;

import ang.mois.pc.entity.PcType;
import ang.mois.pc.repository.PcTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcTypeService {
    private final PcTypeRepository pcTypeRepository;

    public PcTypeService(PcTypeRepository pcTypeRepository) {
        this.pcTypeRepository = pcTypeRepository;
    }

    public List<PcType> getAll() {
        return pcTypeRepository.findAll();
    }
    public PcType getById(Long typeId) {
        return pcTypeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("PcType with id " + typeId + " does not exist"));
    }

    public PcType save(PcType type) {
        // check for duplicities
        if(pcTypeRepository.existsById(type.getId())){
            throw new IllegalArgumentException("PcType with id " + type.getId() + " already exists");
        }
        return pcTypeRepository.save(type);
    }

    public void delete(Long typeId) {
        pcTypeRepository.deleteById(typeId);
    }
}
