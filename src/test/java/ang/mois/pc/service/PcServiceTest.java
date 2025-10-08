package ang.mois.pc.service;
import ang.mois.pc.dto.PcFullDocument;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
import ang.mois.pc.repository.PcRepository;
import ang.mois.pc.repository.PcTypeRepository;
import ang.mois.pc.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class PcServiceTest {

    @Mock
    private PcRepository pcRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private PcTypeRepository pcTypeRepository;

    @InjectMocks
    private PcService pcService;

    private Pc pc;
    private Room room;
    private PcType pcType;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        room = new Room();
        room.setId("r1");
        room.setName("Room A");
        room.setFaculty("Engineering");

        pcType = new PcType();
        pcType.setId("t1");
        pcType.setName("Gaming PC");
        pcType.setCpu("i7");
        pcType.setMemory("16GB");
        pcType.setGpu("RTX 4060");
        pcType.setOs("Windows");

        pc = new Pc();
        pc.setId("p1");
        pc.setStatus("ok");
        pc.setRoomId(room.getId());
        pc.setTypeId(pcType.getId());
    }

    // -------------------------------------------------------------
    // getAllPcsWithDetails()
    // -------------------------------------------------------------
    @Test
    void getAllPcsWithDetails_ShouldReturnMappedFullDocuments() {
        when(pcRepository.findAll()).thenReturn(List.of(pc));
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(pcTypeRepository.findById(pcType.getId())).thenReturn(Optional.of(pcType));

        List<PcFullDocument> result = pcService.getAllPcsWithDetails();
        assertEquals(1, result.size());
        PcFullDocument fullPc = result.getFirst();
        assertEquals(pc.getId(), fullPc.id());
        assertEquals(pc.getStatus(), fullPc.status());
        assertEquals(room.getName(), fullPc.room().getName());
        assertEquals(pcType.getName(), fullPc.type().getName());
    }

    // -------------------------------------------------------------
    // getAllPcsByFacultyWithDetails()
    // -------------------------------------------------------------
    @Test
    void getAllPcsByFacultyWithDetails_ShouldReturnMappedResults() {
        when(roomRepository.findByFaculty(room.getFaculty())).thenReturn(List.of(room));
        when(pcRepository.findByRoomIdIsIn(List.of(room.getId()))).thenReturn(List.of(pc));
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));
        when(pcTypeRepository.findById(room.getId())).thenReturn(Optional.of(pcType));

        List<PcFullDocument> result = pcService.getAllPcsByFacultyWithDetails(room.getFaculty());

        assertEquals(1, result.size());
        assertEquals(room.getName(), result.getFirst().room().getName());
        verify(pcRepository).findByRoomIdIsIn(List.of(room.getId()));
    }

    // -------------------------------------------------------------
    // getAllPcsByTypeWithDetails()
    // -------------------------------------------------------------
    @Test
    void getAllPcsByTypeWithDetails_ShouldReturnMappedResults() {
        when(pcRepository.findByTypeId(pcType.getId())).thenReturn(List.of(pc));
        when(roomRepository.findById(room.getId())).thenReturn(Optional.of(room));

        List<PcFullDocument> result = pcService.getAllPcsByTypeWithDetails(pcType);

        assertEquals(1, result.size());
        assertEquals(pcType.getName(), result.getFirst().type().getName());
        assertEquals(room.getName(), result.getFirst().room().getName());
    }

    // -------------------------------------------------------------
    // savePc()
    // -------------------------------------------------------------
    @Test
    void savePc_ShouldThrowException_WhenRoomDoesNotExist() {
        when(roomRepository.existsById(room.getId())).thenReturn(false);
        when(pcTypeRepository.existsById(pcType.getId())).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> pcService.savePc(pc));
        assertEquals("Room with id r1 does not exist", ex.getMessage());
    }

    @Test
    void savePc_ShouldThrowException_WhenTypeDoesNotExist() {
        when(roomRepository.existsById(room.getId())).thenReturn(true);
        when(pcTypeRepository.existsById(pcType.getId())).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> pcService.savePc(pc));
        assertEquals("PcType with id t1 does not exist", ex.getMessage());
    }

    @Test
    void savePc_ShouldSaveSuccessfully_WhenValidReferencesExist() {
        when(roomRepository.existsById(room.getId())).thenReturn(true);
        when(pcTypeRepository.existsById(pcType.getId())).thenReturn(true);
        when(pcRepository.save(pc)).thenReturn(pc);

        Pc saved = pcService.savePc(pc);

        assertNotNull(saved);
        verify(pcRepository).save(pc);
    }

    // -------------------------------------------------------------
    // deletePc()
    // -------------------------------------------------------------
    @Test
    void deletePc_ShouldCallRepositoryDelete() {
        pcService.deletePc(room.getId());
        verify(pcRepository).deleteById(room.getId());
    }

    // -------------------------------------------------------------
    // Basic getters (no logic)
    // -------------------------------------------------------------
    @Test
    void getAllPcs_ShouldReturnListFromRepository() {
        when(pcRepository.findAll()).thenReturn(List.of(pc));
        List<Pc> result = pcService.getAllPcs();
        assertEquals(1, result.size());
    }

    @Test
    void getPcsByRoom_ShouldCallRepository() {
        when(pcRepository.findByRoomId(room.getId())).thenReturn(List.of(pc));
        List<Pc> result = pcService.getPcsByRoom(room.getId());
        assertEquals(1, result.size());
    }

    @Test
    void getPcsByType_ShouldCallRepository() {
        when(pcRepository.findByTypeId(pcType.getId())).thenReturn(List.of(pc));
        List<Pc> result = pcService.getPcsByType(pcType.getId());
        assertEquals(1, result.size());
    }
}

