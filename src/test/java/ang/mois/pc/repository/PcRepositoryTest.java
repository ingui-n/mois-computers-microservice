package ang.mois.pc.repository;

import ang.mois.pc.entity.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PcRepositoryTest {

    @Autowired
    private PcRepository pcRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private PcTypeRepository pcTypeRepository;

    @Test
    void testSaveAndFindById() {
        Faculty faculty = facultyRepository.save(new Faculty("Faculty of IT", "FIT"));
        Room room = roomRepository.save(new Room("A101", faculty));
        PcType type = pcTypeRepository.save(new PcType("Office", "i5", "16GB", "GTX 1060", "Windows"));

        Pc pc = new Pc("ok", Status.OK, room, type);
        Pc saved = pcRepository.save(pc);

        Optional<Pc> found = pcRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getStatus()).isEqualTo(Status.OK);
        assertThat(found.get().getRoom().getName()).isEqualTo("A101");
        assertThat(found.get().getPcType().getCpu()).isEqualTo("i5");
    }

    @Test
    void testFindByStatus() {
        Faculty faculty = facultyRepository.save(new Faculty("Faculty of Science", "FS"));
        Room room = roomRepository.save(new Room("B102", faculty));
        PcType type = pcTypeRepository.save(new PcType("Gaming", "Ryzen 9", "32GB", "RTX 4080", "Windows 11"));

        Pc okPc = pcRepository.save(new Pc("ok", Status.OK, room, type));
        Pc brokenPc = pcRepository.save(new Pc("broken", Status.BROKEN, room, type));

        List<Pc> ok = pcRepository.findByStatus(Status.OK);
        List<Pc> broken = pcRepository.findByStatus(Status.BROKEN);

        assertThat(ok).containsExactly(okPc);
        assertThat(broken).containsExactly(brokenPc);
    }

    @Test
    void testFindByRoom() {
        Faculty faculty = facultyRepository.save(new Faculty("Faculty of Engineering", "FE"));
        Room room1 = roomRepository.save(new Room("C201", faculty));
        Room room2 = roomRepository.save(new Room("C202", faculty));
        PcType type = pcTypeRepository.save(new PcType("Workstation", "i7", "32GB", "RTX 3070", "Linux"));

        Pc pc1 = pcRepository.save(new Pc("ok", Status.OK, room1, type));
        Pc pc2 = pcRepository.save(new Pc("broken", Status.BROKEN, room2, type));

        List<Pc> pcsRoom1 = pcRepository.findByRoom(room1);
        List<Pc> pcsRoom2 = pcRepository.findByRoom(room2);

        assertThat(pcsRoom1).containsExactly(pc1);
        assertThat(pcsRoom2).containsExactly(pc2);
    }

    @Test
    void testFindAllByRoomIn() {
        Faculty faculty = facultyRepository.save(new Faculty("Faculty of Design", "FD"));
        Room r1 = roomRepository.save(new Room("D101", faculty));
        Room r2 = roomRepository.save(new Room("D102", faculty));
        Room r3 = roomRepository.save(new Room("D103", faculty));
        PcType type = pcTypeRepository.save(new PcType("Design", "M1", "16GB", "Integrated", "macOS"));

        Pc pc1 = pcRepository.save(new Pc("ok", Status.OK, r1, type));
        Pc pc2 = pcRepository.save(new Pc("ok", Status.OK, r2, type));
        pcRepository.save(new Pc("ok", Status.OK, r3, type)); // not included

        List<Pc> found = pcRepository.findAllByRoomIn(List.of(r1, r2));

        assertThat(found).containsExactlyInAnyOrder(pc1, pc2);
    }

    @Test
    @Transactional
    void testFindByPcType() {
        Faculty faculty = facultyRepository.save(new Faculty("Faculty of Business", "FB"));
        Room room = roomRepository.save(new Room("E101", faculty));
        PcType officeType = pcTypeRepository.save(new PcType("Office", "i5", "8GB", "Integrated", "Windows 10"));
        PcType gamingType = pcTypeRepository.save(new PcType("Gaming", "Ryzen 7", "16GB", "RTX 2060", "Windows 11"));

        Pc pc1 = pcRepository.save(new Pc("ok", Status.OK, room, officeType));
        Pc pc2 = pcRepository.save(new Pc("ok", Status.OK, room, gamingType));

        List<Pc> officePcs = pcRepository.findByPcType(officeType);
        List<Pc> gamingPcs = pcRepository.findByPcType(gamingType);

        assertThat(officePcs).containsExactly(pc1);
        assertThat(gamingPcs).containsExactly(pc2);
    }
}
