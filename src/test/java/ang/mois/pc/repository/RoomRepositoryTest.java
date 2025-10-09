package ang.mois.pc.repository;

import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataMongoTest
public class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @BeforeEach
    void setup() {
        roomRepository.save(new Room("Lab 101", Faculty.FIM));
        roomRepository.save(new Room( "Lab 102", Faculty.FIM));
        roomRepository.save(new Room( "Lab 201", Faculty.PrF));
    }
    @AfterEach
    void cleanup() {
        roomRepository.deleteAll();
    }

    @Test
    void testFindAll() {
        List<Room> rooms = roomRepository.findAll();
        assertThat(rooms).hasSize(3);
    }

    @Test
    void testFindByFaculty() {
        List<Room> informaticsRooms = roomRepository.findByFaculty(Faculty.FIM.name());
        assertThat(informaticsRooms).hasSize(2);
        assertThat(informaticsRooms).extracting(Room::getName).containsExactlyInAnyOrder("Lab 101", "Lab 102");
    }
}
