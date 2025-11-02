package ang.mois.pc.controller;

import ang.mois.pc.controller.exception.GlobalExceptionHandler;
import ang.mois.pc.dto.request.FacultyRequestDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.service.FacultyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for FacultyController.
 * We use @WebMvcTest to load only the controller and related web components.
 * We MUST import GlobalExceptionHandler.class so that our
 * MethodArgumentNotValidException is handled correctly and returns a 400.
 */
@WebMvcTest(controllers = {FacultyController.class, GlobalExceptionHandler.class})
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Used to convert DTOs to JSON strings

    @MockitoBean
    private FacultyService facultyService;

    private FacultyRequestDto validDto;
    private Faculty facultyEntity;

    private final String apiPath = "/faculty";

    @BeforeEach
    void setUp() {
        // A helper to create a fully valid DTO for OnCreate
        validDto = new FacultyRequestDto(
                "Faculty of Informatics",
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5,
                90,
                180
        );

        // A mock entity to be returned by the service
        facultyEntity = new Faculty();
    }

    /* POST /faculty tests */

    @Test
    void addValidFaculty() throws Exception {
        when(facultyService.save(any(FacultyRequestDto.class))).thenReturn(facultyEntity);

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(facultyService, times(1)).save(any(FacultyRequestDto.class));
    }

    @Test
    void addInvalidFaculty_BlankName() throws Exception {
        // Create a DTO that violates an OnCreate rule
        FacultyRequestDto invalidDto = new FacultyRequestDto(
                "", // Blank name
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5, 90, 180
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"));

        // Verify service was never called
        verify(facultyService, never()).save(any(FacultyRequestDto.class));
    }

    @Test
    void addInvalidFaculty_TimeIsNull() throws Exception {
        // Create a DTO that violates an OnCreate rule
        FacultyRequestDto invalidDto = new FacultyRequestDto(
                "Faculty of Informatics",
                "FI",
                null, // Null start time
                Time.valueOf("20:00:00"),
                5, 90, 180
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reservationTimeStart").value("Reservation start time is mandatory"));

        verify(facultyService, never()).save(any(FacultyRequestDto.class));
    }

    // --- PUT /faculty/{id} (Default Validation) Test

    @Test
    void updateFaculty() throws Exception {
        // This DTO would FAIL OnCreate validation but should PASS Default validation
        FacultyRequestDto partialDto = new FacultyRequestDto(
                "Updated Faculty Name",
                null, // This is allowed on update
                null, // This is allowed on update
                null,
                null,
                null,
                null
        );

        when(facultyService.update(eq(1L), any(FacultyRequestDto.class))).thenReturn(facultyEntity);

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialDto)))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).update(eq(1L), any(FacultyRequestDto.class));
    }

    @Test
    void updateFacultyInvalid_NameIsTooShort() throws Exception {
        FacultyRequestDto invalidPartialDto = new FacultyRequestDto(
                "A", // Too short
                null,
                null,
                null,
                null,
                null,
                null
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"));

        verify(facultyService, never()).update(anyLong(), any(FacultyRequestDto.class));
    }

    @Test
    void updateFacultyInvalid_ReservationCountIsNegative() throws Exception {
        // This DTO violates a Default rule (@Min(value=0))
        FacultyRequestDto invalidPartialDto = new FacultyRequestDto(
                "A valid name",
                null,
                null,
                null,
                -1, // Invalid
                null,
                null
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.maxUserReservationCount").value("Reservation count must be zero or positive"));

        verify(facultyService, never()).update(anyLong(), any(FacultyRequestDto.class));
    }
    /* --- GET /faculty tests --- */

    @Test
    void getAllFaculties() throws Exception {
        when(facultyService.getAll()).thenReturn(List.of(facultyEntity));

        mockMvc.perform(get(apiPath))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).getAll();
    }

    @Test
    void getFacultyById() throws Exception {
        when(facultyService.getById(1L)).thenReturn(facultyEntity);

        mockMvc.perform(get(apiPath.concat("/1")))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).getById(1L);
    }

    /* --- DELETE /faculty/{id} tests --- */

    @Test
    void deleteFaculty() throws Exception {
        doNothing().when(facultyService).delete(1L);

        mockMvc.perform(delete(apiPath.concat("/1")))
                .andExpect(status().isNoContent());

        verify(facultyService, times(1)).delete(1L);
    }
}

