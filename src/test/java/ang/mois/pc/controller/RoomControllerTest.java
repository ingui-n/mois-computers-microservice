package ang.mois.pc.controller;

import ang.mois.pc.controller.exception.GlobalExceptionHandler;
import ang.mois.pc.dto.request.RoomRequestDto;
import ang.mois.pc.dto.response.FacultyResponseDto;
import ang.mois.pc.dto.response.RoomResponseDto;
import ang.mois.pc.service.FacultyService;
import ang.mois.pc.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {RoomController.class, GlobalExceptionHandler.class})
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RoomService roomService;

    @MockitoBean
    private FacultyService facultyService;

    private RoomRequestDto validDto;
    private RoomResponseDto roomResponseDto;
    private FacultyResponseDto faculty;
    private final String apiPath = "/computerRoom";

    @BeforeEach
    void setUp() {
        validDto = new RoomRequestDto(
                "Main Lab",
                1L
        );

        roomResponseDto = new RoomResponseDto(
                1L,
                1L,
                "Main Lab",
                LocalDateTime.now()
        );
        faculty = new FacultyResponseDto(
                1L,
                "Faculty of Informatics",
                "FI",
                Time.valueOf("08:00:00"),
                Time.valueOf("20:00:00"),
                5,
                90,
                180,
                LocalDateTime.now()
        );
    }

    /* POST /computerRoom tests */
    @Test
    void addValid() throws Exception {
        when(roomService.save(any(RoomRequestDto.class))).thenReturn(roomResponseDto);

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(roomService, times(1)).save(any(RoomRequestDto.class));
    }

    @Test
    void addInvalid_BlankName() throws Exception {
        RoomRequestDto invalidDto = new RoomRequestDto(
                "", // blank name
                1L
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"));

        verify(roomService, never()).save(any(RoomRequestDto.class));
    }

    @Test
    void addInvalid_NullFacultyId() throws Exception {
        RoomRequestDto invalidDto = new RoomRequestDto(
                "Main Lab",
                null // missing facultyId
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.facultyId").value("Faculty Id is mandatory"));

        verify(roomService, never()).save(any(RoomRequestDto.class));
    }

    /* PUT /computerRoom/{id} tests */
    @Test
    void updateValid() throws Exception {
        when(roomService.update(eq(1L), any(RoomRequestDto.class))).thenReturn(roomResponseDto);
        RoomRequestDto partialDto = new RoomRequestDto(
                "Updated Lab",
                null // allowed null on update
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialDto)))
                .andExpect(status().isOk());

        verify(roomService, times(1)).update(eq(1L), any(RoomRequestDto.class));
    }

    @Test
    void updateInvalid_NameTooShort() throws Exception {
        RoomRequestDto invalidPartialDto = new RoomRequestDto(
                "-", // too short
                null
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"));

        verify(roomService, never()).update(eq(1L), any(RoomRequestDto.class));
    }

    /* GET /computerRoom and /computerRoom?facultyId= tests */
    @Test
    void getAllRooms() throws Exception {
        when(roomService.getAll()).thenReturn(List.of(roomResponseDto));

        mockMvc.perform(get(apiPath))
                .andExpect(status().isOk());

        verify(roomService, times(1)).getAll();
    }

    @Test
    void getRoomsByFaculty() throws Exception {
        when(facultyService.getById(1L)).thenReturn(faculty);
        when(roomService.getByFaculty(faculty.id())).thenReturn(List.of(roomResponseDto));

        mockMvc.perform(get(apiPath).param("facultyId", "1"))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).getById(1L);
        verify(roomService, times(1)).getByFaculty(faculty.id());
    }

    /* DELETE /computerRoom/{id} */
    @Test
    void deleteRoom() throws Exception {
        doNothing().when(roomService).delete(1L);

        mockMvc.perform(delete(apiPath.concat("/1")))
                .andExpect(status().isNoContent());

        verify(roomService, times(1)).delete(1L);
    }
}
