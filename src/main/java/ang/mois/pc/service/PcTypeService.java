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

    public List<PcType> getAllTypes() {
        return pcTypeRepository.findAll();
    }

    public PcType saveType(PcType type) {
        // check for duplicities
        if(pcTypeRepository.existsById(type.getId())){
            throw new IllegalArgumentException("PcType with id " + type.getId() + " already exists");
        }
        return pcTypeRepository.save(type);
    }

    public void deleteType(String typeId) {
        // todo later check for references from pcs ?
        pcTypeRepository.deleteById(typeId);
    }
}
