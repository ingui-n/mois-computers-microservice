package ang.mois.pc.service;

import ang.mois.pc.dto.request.PcTypeRequestDto;
import ang.mois.pc.dto.response.PcTypeResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
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
        PcTypeResponseDto type = pcTypeService.save(validDto);
        assertNotNull(type);
        verifyParams(type, validDto);
    }

    @Test
    void getById() {
        PcTypeResponseDto type = pcTypeService.save(validDto);
        PcTypeResponseDto fetched = pcTypeService.getById(type.id());
        verifyParams(fetched, validDto);
    }

    @Test
    void update() {
        PcTypeResponseDto type = pcTypeService.save(validDto);

        PcTypeRequestDto updateDto = new PcTypeRequestDto(
                "Office PC",
                null,
                "8GB",
                null
        );

        PcTypeResponseDto updated = pcTypeService.update(type.id(), updateDto);
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
        PcTypeResponseDto type = pcTypeService.save(validDto);
        Long id = type.id();

        pcTypeService.delete(id);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> pcTypeService.getById(id));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    private void verifyParams(PcTypeResponseDto output, PcTypeRequestDto expected) {
        assertEquals(expected.name(), output.name());
        assertEquals(expected.cpu(), output.cpu());
        assertEquals(expected.ram(), output.ram());
        assertEquals(expected.gpu(), output.gpu());
    }
}

