package ang.mois.pc.repository;

import ang.mois.pc.entity.Pc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
public class PcRepositoryTest {

    @Autowired
    private PcRepository pcRepository;

    @Test
    void savePc() {
       pcRepository.deleteAll();

       Pc pc = new Pc( "ok", "type1", "roomA");
       pcRepository.save(pc);
       String id = pc.getId();
       Optional<Pc> pcOpt = pcRepository.findById(id);
       assertTrue(pcOpt.isPresent());
       assertEquals("ok", pcOpt.get().getStatus());
       assertEquals("type1", pcOpt.get().getTypeId());
       assertEquals("roomA", pcOpt.get().getRoomId());
    }

    @Test
    void testFindByStatus() {
        setup();
        List<Pc> okPCs = pcRepository.findByStatus("ok");
        assertThat(okPCs).hasSize(2);
    }

    @Test
    void testFindByRoomId() {
        setup();
        List<Pc> roomAPCs = pcRepository.findByRoomId("roomA");
        assertThat(roomAPCs).hasSize(2);
    }

    @Test
    void testFindByTypeId() {
        setup();
        List<Pc> type1PCs = pcRepository.findByTypeId("type1");
        assertThat(type1PCs).hasSize(2);
    }

    @Test
    void testFindByRoomIdIn() {
        setup();
        List<Pc> pcsAcrossRooms = pcRepository.findByRoomIdIsIn(List.of("roomB", "roomC"));
        assertThat(pcsAcrossRooms).hasSize(2);
        assertTrue(pcsAcrossRooms.stream().anyMatch(pc -> pc.getRoomId().equals("roomB")));
        assertTrue(pcsAcrossRooms.stream().anyMatch(pc -> pc.getRoomId().equals("roomC")));
    }

    void setup() {
        pcRepository.save(new Pc( "ok", "type1", "roomA"));
        pcRepository.save(new Pc("broken", "type1", "roomB"));
        pcRepository.save(new Pc("ok", "type2", "roomA"));
        pcRepository.save(new Pc("ok", "type2", "roomC"));
    }

    @AfterEach
    void cleanup() {
        pcRepository.deleteAll();
    }
}
