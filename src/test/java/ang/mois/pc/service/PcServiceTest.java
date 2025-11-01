package ang.mois.pc.service;

import ang.mois.pc.dto.PcDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
import ang.mois.pc.repository.FacultyRepository;
import ang.mois.pc.repository.PcRepository;
import ang.mois.pc.repository.RoomRepository;
import ang.mois.pc.repository.PcTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PcServiceTest {

    @Autowired
    private PcService pcService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private PcTypeRepository pcTypeRepository;

    @Autowired
    private PcRepository pcRepository;

    private PcDto validDto;
    private Room savedRoom;
    private Faculty savedFaculty;
    private PcType savedType;

    @BeforeEach
    void setUp() {
        // prepare related entities
        savedFaculty = new Faculty();
        facultyRepository.save(savedFaculty);

        savedRoom = new Room();
        savedRoom.setName("Main Room");
        savedRoom.setFaculty(savedFaculty);
        savedRoom = roomRepository.save(savedRoom);

        savedType = new PcType();
        savedType.setName("Gaming Rig");
        savedType.setCpu("Ryzen 9");
        savedType.setRam("32GB");
        savedType.setGpu("RTX 4080");
        savedType = pcTypeRepository.save(savedType);

        validDto = new PcDto(
                "High-End PC",
                Boolean.TRUE,
                savedRoom.getId(),
                savedType.getId()
        );
    }

    @Test
    void save() {
        Pc pc = pcService.save(validDto);

        assertNotNull(pc);
        assertEquals(validDto.name(), pc.getName());
        assertEquals(validDto.available(), pc.isAvailable());
        assertNotNull(pc.getRoom());
        assertNotNull(pc.getPcType());
        assertEquals(savedRoom.getId(), pc.getRoom().getId());
        assertEquals(savedType.getId(), pc.getPcType().getId());
    }

    @Test
    void getById() {
        Pc pc = pcService.save(validDto);
        Pc found = pcService.getById(pc.getId());

        assertNotNull(found);
        assertEquals(pc.getId(), found.getId());
        assertEquals(pc.getName(), found.getName());
    }

    @Test
    void update() {
        Pc pc = pcService.save(validDto);

        // prepare a DTO that updates the name and sets availability false
        PcDto updateDto = new PcDto(
                "Updated Name",
                Boolean.FALSE,
                null, // keep old room
                null  // keep old type
        );

        Pc updated = pcService.update(pc.getId(), updateDto);

        assertEquals("Updated Name", updated.getName());
        assertFalse(updated.isAvailable());
        assertEquals(pc.getRoom().getId(), updated.getRoom().getId());
        assertEquals(pc.getPcType().getId(), updated.getPcType().getId());
    }

    @Test
    void update_changeRelations() {
        Pc pc = pcService.save(validDto);

        // new relations
        Room newRoom = new Room();
        newRoom.setName("Another Room");
        newRoom.setFaculty(savedFaculty);
        newRoom = roomRepository.save(newRoom);

        PcType newType = new PcType();
        newType.setName("Office PC");
        newType.setCpu("i5");
        newType.setRam("16GB");
        newType.setGpu("GTX 1650");
        newType = pcTypeRepository.save(newType);

        PcDto updateDto = new PcDto(
                "Moved PC",
                Boolean.TRUE,
                newRoom.getId(),
                newType.getId()
        );

        Pc updated = pcService.update(pc.getId(), updateDto);

        assertEquals("Moved PC", updated.getName());
        assertEquals(newRoom.getId(), updated.getRoom().getId());
        assertEquals(newType.getId(), updated.getPcType().getId());
    }

    @Test
    void getByRoom() {
        Pc pc = pcService.save(validDto);
        List<Pc> pcs = pcService.getByRoom(savedRoom);

        assertFalse(pcs.isEmpty());
        assertTrue(pcs.stream().anyMatch(p -> p.getId().equals(pc.getId())));
    }

    @Test
    void getByType() {
        Pc pc = pcService.save(validDto);
        List<Pc> pcs = pcService.getByType(savedType);

        assertFalse(pcs.isEmpty());
        assertTrue(pcs.stream().anyMatch(p -> p.getId().equals(pc.getId())));
    }

    @Test
    void delete() {
        Pc pc = pcService.save(validDto);
        pcService.delete(pc.getId());

        assertFalse(pcRepository.findById(pc.getId()).isPresent());
    }

    @Test
    void getById_nonexistent_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> pcService.getById(9999L));
    }

    @Test
    void save_invalidRoom_shouldThrow() {
        PcDto dto = new PcDto(
                "Bad PC",
                Boolean.TRUE,
                9999L, // nonexistent room
                savedType.getId()
        );
        assertThrows(IllegalArgumentException.class, () -> pcService.save(dto));
    }

    @Test
    void save_invalidType_shouldThrow() {
        PcDto dto = new PcDto(
                "Bad PC",
                Boolean.TRUE,
                savedRoom.getId(),
                9999L // nonexistent type
        );
        assertThrows(IllegalArgumentException.class, () -> pcService.save(dto));
    }
}
