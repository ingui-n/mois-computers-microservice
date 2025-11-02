package ang.mois.pc.service;

import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.dto.request.RoomRequestDto;
import ang.mois.pc.dto.response.FacultyResponseDto;
import ang.mois.pc.entity.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalDate;

@SpringBootTest
class RoomServiceTest {

    @Autowired
    private RoomService roomService;

    @Autowired
    private FacultyService facultyService;

    private FacultyResponseDto faculty;
    private RoomRequestDto validDto;

    @BeforeEach
    void setUp() {
        // Create and store a faculty to attach to the room
        faculty = facultyService.save(new FacultyRequestDto(
                "Faculty of Informatics",
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5,
                90,
                180
        ));

        validDto = new RoomRequestDto(
                "Room A",
                faculty.id()
        );
    }

    @Test
    void save() {
        Room room = roomService.save(validDto);
        assertNotNull(room);
        verifyParams(room, validDto);
    }

    @Test
    void getById() {
        Room saved = roomService.save(validDto);
        Room fetched = roomService.getById(saved.getId());
        verifyParams(fetched, validDto);
    }

    @Test
    void update() {
        Room saved = roomService.save(validDto);

        RoomRequestDto updateDto = new RoomRequestDto(
                "Updated Room",
                null
        );

        Room updated = roomService.update(saved.getId(), updateDto);

        RoomRequestDto expected = new RoomRequestDto(
                "Updated Room",
                faculty.id()
        );
        verifyParams(updated, expected);
    }

    @Test
    void getByFaculty() {
        roomService.save(validDto);
        var rooms = roomService.getByFaculty(faculty.id());
        assertFalse(rooms.isEmpty());
        assertEquals(faculty.id(), rooms.getFirst().getFaculty().getId());
    }

    @Test
    void delete() {
        Room saved = roomService.save(validDto);
        Long id = saved.getId();
        roomService.delete(id);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> roomService.getById(id));
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    private void verifyParams(Room room, RoomRequestDto dto) {
        assertEquals(dto.name(), room.getName());
        assertEquals(dto.facultyId(), room.getFaculty().getId());
        assertEquals(LocalDate.now(), room.getCreatedAt().toLocalDate());
    }
}
