package ang.mois.pc.service;

import ang.mois.pc.dto.request.PcTypeRequestDto;
import ang.mois.pc.dto.response.PcTypeResponseDto;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.mapper.PcTypeMapper;
import ang.mois.pc.repository.PcTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcTypeService {
    private final PcTypeRepository pcTypeRepository;
    private final PcTypeMapper pcTypeMapper;

    public PcTypeService(PcTypeRepository pcTypeRepository, PcTypeMapper pcTypeMapper) {
        this.pcTypeRepository = pcTypeRepository;
        this.pcTypeMapper = pcTypeMapper;
    }

    public List<PcTypeResponseDto> getAll() {
        return pcTypeMapper.toResponseDtoList(pcTypeRepository.findAll());
    }

    public PcTypeResponseDto getById(Long typeId) {
        PcType type = getPcType(typeId);
        return pcTypeMapper.toResponseDto(type);
    }

    public PcTypeResponseDto save(PcTypeRequestDto type) {
        PcType pcType = pcTypeMapper.toEntity(type);
        return pcTypeMapper.toResponseDto(pcTypeRepository.save(pcType));
    }

    public void delete(Long typeId) {
        pcTypeRepository.deleteById(typeId);
    }

    public PcTypeResponseDto update(Long id, PcTypeRequestDto pcTypeDto) {
        PcType pcTypeEntity = getPcType(id);

        // map by copying non-null values from update dto
        pcTypeMapper.updateEntityFromDto(pcTypeDto, pcTypeEntity);

        return pcTypeMapper.toResponseDto(pcTypeRepository.save(pcTypeEntity));
    }

    private PcType getPcType(Long id) {
        return pcTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PcType with id " + id + " does not exist"));
    }
}
