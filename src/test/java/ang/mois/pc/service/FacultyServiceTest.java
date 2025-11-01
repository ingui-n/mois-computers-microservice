package ang.mois.pc.service;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.entity.Faculty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalDate;

@SpringBootTest
class FacultyServiceTest {
    @Autowired
    private FacultyService facultyService;

    private FacultyDto validDto;

    @BeforeEach
    void setUp() {
        // A helper to create a fully valid DTO
        validDto = new FacultyDto(
                "Faculty of Informatics",
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5,
                90,
                180
        );
    }

    @Test
    void save() {
        // store the faculty
        Faculty faculty = facultyService.save(validDto);
        assertNotNull(faculty);

        verifyParams(faculty, validDto);
    }

    @Test
    void getById() {
        // pre-store the faculty
        Faculty faculty = facultyService.save(validDto);

        faculty = facultyService.getById(faculty.getId());
        verifyParams(faculty, validDto);
    }

    @Test
    void update() {
        // pre-store the faculty
        Faculty faculty = facultyService.save(validDto);

        // prepare dto
        FacultyDto updateDto = new FacultyDto("New Name",
                null, null, null,
                null, null, null);

        Faculty updated = facultyService.update(faculty.getId(), updateDto);
        FacultyDto mergedDto = new FacultyDto(
                "New Name",
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5,
                90,
                180
        );
        verifyParams(updated, mergedDto);
    }

    private void verifyParams(Faculty faculty, FacultyDto dto) {
        assertEquals(faculty.getName(), dto.name());
        assertEquals(faculty.getShortcut(), dto.shortcut());
        assertEquals(faculty.getReservationTimeStart(), dto.reservationTimeStart());
        assertEquals(faculty.getReservationTimeEnd(), dto.reservationTimeEnd());
        assertEquals(faculty.getMaxUserReservationCount(), dto.maxUserReservationCount());
        assertEquals(faculty.getMaxUserReservationTime(), dto.maxUserReservationTime());
        assertEquals(faculty.getMaxUserReservationTimeWeekly(), dto.maxUserReservationTimeWeekly());

        // verify that the createdAt filed was set correctly
        assertEquals(LocalDate.now(), faculty.getCreatedAt().toLocalDate());
    }
}

