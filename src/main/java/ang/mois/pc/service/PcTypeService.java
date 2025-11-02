package ang.mois.pc.service;

import ang.mois.pc.dto.request.PcTypeRequestDto;
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

    public PcType save(PcTypeRequestDto type) {
        PcType pcType = new PcType(
                type.name(),
                type.cpu(),
                type.ram(),
                type.gpu()
        );
        return pcTypeRepository.save(pcType);
    }

    public void delete(Long typeId) {
        pcTypeRepository.deleteById(typeId);
    }

    public PcType update(Long id, PcTypeRequestDto pcType) {
        PcType type = getById(id);
        if (pcType.name() != null) type.setName(pcType.name());
        if (pcType.cpu() != null) type.setCpu(pcType.cpu());
        if (pcType.ram() != null) type.setRam(pcType.ram());
        if (pcType.gpu() != null) type.setGpu(pcType.gpu());
        return pcTypeRepository.save(type);
    }
}
