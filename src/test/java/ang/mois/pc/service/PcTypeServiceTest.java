package ang.mois.pc.service;

import ang.mois.pc.dto.request.PcTypeRequestDto;
import ang.mois.pc.entity.PcType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PcTypeServiceTest {

    @Autowired
    private PcTypeService pcTypeService;

    private PcTypeRequestDto validDto;

    @BeforeEach
    void setUp() {
        validDto = new PcTypeRequestDto(
                "Gaming PC",
                "Intel i7",
                "16GB",
                "RTX 4070"
        );
    }

    @Test
    void save() {
        PcType type = pcTypeService.save(validDto);
        assertNotNull(type);
        verifyParams(type, validDto);
    }

    @Test
    void getById() {
        PcType type = pcTypeService.save(validDto);
        PcType fetched = pcTypeService.getById(type.getId());
        verifyParams(fetched, validDto);
    }

    @Test
    void update() {
        PcType type = pcTypeService.save(validDto);

        PcTypeRequestDto updateDto = new PcTypeRequestDto(
                "Office PC",
                null,
                "8GB",
                null
        );

        PcType updated = pcTypeService.update(type.getId(), updateDto);
        PcTypeRequestDto merged = new PcTypeRequestDto(
                "Office PC",
                "Intel i7",
                "8GB",
                "RTX 4070"
        );

        verifyParams(updated, merged);
    }

    @Test
    void getAll() {
        pcTypeService.save(validDto);
        var types = pcTypeService.getAll();
        assertFalse(types.isEmpty());
    }

    @Test
    void delete() {
        PcType type = pcTypeService.save(validDto);
        Long id = type.getId();

        pcTypeService.delete(id);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> pcTypeService.getById(id));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    private void verifyParams(PcType type, PcTypeRequestDto dto) {
        assertEquals(dto.name(), type.getName());
        assertEquals(dto.cpu(), type.getCpu());
        assertEquals(dto.ram(), type.getRam());
        assertEquals(dto.gpu(), type.getGpu());
    }
}

