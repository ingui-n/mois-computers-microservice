package ang.mois.pc.controller;

import ang.mois.pc.controller.exception.GlobalExceptionHandler;
import ang.mois.pc.dto.RoomDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
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

    private RoomDto validDto;
    private Room roomEntity;
    private Faculty faculty;
    private final String apiPath = "/computerRoom";

    @BeforeEach
    void setUp() {
        validDto = new RoomDto(
                "Main Lab",
                1L
        );

        roomEntity = new Room();
        faculty = new Faculty();
    }

    /* POST /computerRoom tests */
    @Test
    void addValid() throws Exception {
        when(roomService.save(any(RoomDto.class))).thenReturn(roomEntity);

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(roomService, times(1)).save(any(RoomDto.class));
    }

    @Test
    void addInvalid_BlankName() throws Exception {
        RoomDto invalidDto = new RoomDto(
                "", // blank name
                1L
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"));

        verify(roomService, never()).save(any(RoomDto.class));
    }

    @Test
    void addInvalid_NullFacultyId() throws Exception {
        RoomDto invalidDto = new RoomDto(
                "Main Lab",
                null // missing facultyId
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.facultyId").value("Faculty Id is mandatory"));

        verify(roomService, never()).save(any(RoomDto.class));
    }

    /* PUT /computerRoom/{id} tests */
    @Test
    void updateValid() throws Exception {
        when(roomService.update(eq(1L), any(RoomDto.class))).thenReturn(roomEntity);
        RoomDto partialDto = new RoomDto(
                "Updated Lab",
                null // allowed null on update
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialDto)))
                .andExpect(status().isOk());

        verify(roomService, times(1)).update(eq(1L), any(RoomDto.class));
    }

    @Test
    void updateInvalid_NameTooShort() throws Exception {
        RoomDto invalidPartialDto = new RoomDto(
                "-", // too short
                null
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"));

        verify(roomService, never()).update(eq(1L), any(RoomDto.class));
    }

    /* GET /computerRoom and /computerRoom?facultyId= tests */
    @Test
    void getAllRooms() throws Exception {
        when(roomService.getAll()).thenReturn(List.of(roomEntity));

        mockMvc.perform(get(apiPath))
                .andExpect(status().isOk());

        verify(roomService, times(1)).getAll();
    }

    @Test
    void getRoomsByFaculty() throws Exception {
        when(facultyService.getById(1L)).thenReturn(faculty);
        when(roomService.getByFaculty(faculty)).thenReturn(List.of(roomEntity));

        mockMvc.perform(get(apiPath).param("facultyId", "1"))
                .andExpect(status().isOk());

        verify(facultyService, times(1)).getById(1L);
        verify(roomService, times(1)).getByFaculty(faculty);
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
