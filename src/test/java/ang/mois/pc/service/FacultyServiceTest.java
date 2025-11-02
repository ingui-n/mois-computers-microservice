package ang.mois.pc.service;

import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.dto.response.FacultyResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Time;
import java.time.LocalDate;

@SpringBootTest
@Transactional
class FacultyServiceTest {
    @Autowired
    private FacultyService facultyService;

    private FacultyRequestDto validDto;

    @BeforeEach
    void setUp() {
        // A helper to create a fully valid DTO
        validDto = new FacultyRequestDto(
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
        FacultyResponseDto faculty = facultyService.save(validDto);
        assertNotNull(faculty);

        verifyParams(faculty, validDto);
    }

    @Test
    void getById() {
        // pre-store the faculty
        FacultyResponseDto faculty = facultyService.save(validDto);

        faculty = facultyService.getById(faculty.id());
        verifyParams(faculty, validDto);
    }

    @Test
    void update() {
        // pre-store the faculty
        FacultyResponseDto faculty = facultyService.save(validDto);

        // prepare dto
        FacultyRequestDto updateDto = new FacultyRequestDto("New Name",
                null, null, null,
                null, null, null);

        FacultyResponseDto updated = facultyService.update(faculty.id(), updateDto);
        FacultyRequestDto mergedDto = new FacultyRequestDto(
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

    private void verifyParams(FacultyResponseDto faculty, FacultyRequestDto dto) {
        assertEquals(faculty.name(), dto.name());
        assertEquals(faculty.shortcut(), dto.shortcut());
        assertEquals(faculty.reservationTimeStart(), dto.reservationTimeStart());
        assertEquals(faculty.reservationTimeEnd(), dto.reservationTimeEnd());
        assertEquals(faculty.maxUserReservationCount(), dto.maxUserReservationCount());
        assertEquals(faculty.maxUserReservationTime(), dto.maxUserReservationTime());
        assertEquals(faculty.maxUserReservationTimeWeekly(), dto.maxUserReservationTimeWeekly());

        // verify that the createdAt filed was set correctly
        assertEquals(LocalDate.now(), faculty.createdAt().toLocalDate());
    }
}

