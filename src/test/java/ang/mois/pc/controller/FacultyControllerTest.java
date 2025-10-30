package ang.mois.pc.controller;

import ang.mois.pc.controller.exception.GlobalExceptionHandler;
import ang.mois.pc.dto.FacultyDto;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private FacultyDto validDto;
    private Faculty facultyEntity;

    private final String apiPath = "/faculty";

    @BeforeEach
    void setUp() {
        // 1. A helper to create a fully valid DTO for OnCreate
        validDto = new FacultyDto(
                "Faculty of Informatics",
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5,
                90,
                180
        );

        // 2. A mock entity to be returned by the service
        facultyEntity = new Faculty();
    }

    /* POST /faculty tests */

    @Test
    void addFaculty_WhenDtoIsValid_ShouldReturnCreated() throws Exception {
        // Arrange
        when(facultyService.save(any(FacultyDto.class))).thenReturn(facultyEntity);

        // Act & Assert
        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        // Verify
        verify(facultyService, times(1)).save(any(FacultyDto.class));
    }

    @Test
    void addFaculty_WhenNameIsBlank_ShouldReturnBadRequest() throws Exception {
        // Create a DTO that violates an OnCreate rule
        FacultyDto invalidDto = new FacultyDto(
                "", // Blank name
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5, 90, 180
        );

        // Act & Assert
        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"));

        // Verify service was never called
        verify(facultyService, never()).save(any(FacultyDto.class));
    }

    @Test
    void addFaculty_WhenTimeIsNull_ShouldReturnBadRequest() throws Exception {
        // Create a DTO that violates an OnCreate rule
        FacultyDto invalidDto = new FacultyDto(
                "Faculty of Informatics",
                "FI",
                null, // Null start time
                Time.valueOf("20:00:00"),
                5, 90, 180
        );

        // Act & Assert
        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.reservationTimeStart").value("Reservation start time is mandatory"));

        // Verify service was never called
        verify(facultyService, never()).save(any(FacultyDto.class));
    }

    // --- PUT /faculty/{id} (Default Validation) Test

    @Test
    void updateFaculty_WhenDtoIsPartialAndValid_ShouldReturnOk() throws Exception {
        // This DTO would FAIL OnCreate validation (nulls)
        // but should PASS Default validation.
        FacultyDto partialDto = new FacultyDto(
                "Updated Faculty Name",
                null, // This is allowed on update
                null, // This is allowed on update
                null,
                null,
                null,
                null
        );

        when(facultyService.update(eq(1L), any(FacultyDto.class))).thenReturn(facultyEntity);

        // Act & Assert
        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialDto)))
                .andExpect(status().isOk());

        // Verify service WAS called
        verify(facultyService, times(1)).update(eq(1L), any(FacultyDto.class));
    }

    @Test
    void updateFaculty_WhenNameIsPresentButInvalid_ShouldReturnBadRequest() throws Exception {
        // This DTO violates a Default rule (@Size(min=2))
        FacultyDto invalidPartialDto = new FacultyDto(
                "A", // Too short
                null,
                null,
                null,
                null,
                null,
                null
        );

        // Act & Assert
        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"));

        // Verify service was never called
        verify(facultyService, never()).update(anyLong(), any(FacultyDto.class));
    }

    @Test
    void updateFaculty_WhenReservationCountIsPresentButInvalid_ShouldReturnBadRequest() throws Exception {
        // Arrange
        // This DTO violates a Default rule (@Min(value=0))
        FacultyDto invalidPartialDto = new FacultyDto(
                "A valid name",
                null,
                null,
                null,
                -1, // Invalid
                null,
                null
        );

        // Act & Assert
        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.maxUserReservationCount").value("Reservation count must be zero or positive"));

        // Verify service was never called
        verify(facultyService, never()).update(anyLong(), any(FacultyDto.class));
    }
}

