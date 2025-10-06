package ang.mois.pc.repository;

import ang.mois.pc.entity.PcType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
class PcTypeRepositoryTest {

    @Autowired
    private PcTypeRepository pcTypeRepository;

    @BeforeEach
    void setup() {
        pcTypeRepository.save(new PcType( "Gaming PC", "Intel i7", "16GB", "RTX 3060", "Windows 11"));
        pcTypeRepository.save(new PcType( "Office PC", "Intel i5", "8GB", "Integrated", "Ubuntu"));
    }

    @AfterEach
    void cleanup() {
        pcTypeRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        List<PcType> types = pcTypeRepository.findAll();
        assertThat(types).hasSize(2);
    }

    @Test
    void testSaveAndFindById() {
        PcType type = new PcType( "Dev PC", "Ryzen 7", "32GB", "RTX 3070", "Linux");
        pcTypeRepository.save(type);

        String id = type.getId();
        Optional<PcType> typeOpt = pcTypeRepository.findById(id);

        assertTrue(typeOpt.isPresent());
        PcType savedType = typeOpt.get();
        assertEquals("Dev PC", savedType.getName());
        assertEquals("Ryzen 7", savedType.getCpu());
        assertEquals("32GB", savedType.getMemory());
        assertEquals("RTX 3070", savedType.getGpu());
        assertEquals("Linux", savedType.getOs());
    }

}
