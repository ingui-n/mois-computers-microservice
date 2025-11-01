package ang.mois.pc.controller;

import ang.mois.pc.controller.exception.GlobalExceptionHandler;
import ang.mois.pc.dto.PcDto;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.PcService;
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

@WebMvcTest(controllers = {PcController.class, GlobalExceptionHandler.class})
public class PcControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PcService pcService;

    @MockitoBean
    private RoomService roomService;

    private PcDto validDto;
    private Pc pcEntity;
    private Room roomEntity;
    private final String apiPath = "/computer";

    @BeforeEach
    void setUp() {
        validDto = new PcDto(
                "Gaming Pc - Best one",
                Boolean.TRUE,
                1L,
                1L
        );
        pcEntity = new Pc();
        roomEntity = new Room();
    }

    /* POST /computer tests */
    @Test
    void addValid() throws Exception {
        when(pcService.save(any(PcDto.class))).thenReturn(pcEntity);

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validDto)))
                .andExpect(status().isCreated());

        verify(pcService, times(1)).save(any(PcDto.class));
    }

    @Test
    void addInvalidPc_BlankName() throws Exception {
        PcDto invalidDto = new PcDto(
                "", // blank name
                Boolean.TRUE,
                1L,
                1L
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name is mandatory"));

        verify(pcService, never()).save(any(PcDto.class));
    }

    @Test
    void addInvalidPc_NullAvailability() throws Exception {
        PcDto invalidDto = new PcDto(
                "Some Pc",
                null, // null availability
                1L,
                1L
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.available").value("Available is mandatory"));

        verify(pcService, never()).save(any(PcDto.class));
    }

    @Test
    void addInvalidPc_NullRoomId() throws Exception {
        PcDto invalidDto = new PcDto(
                "Pc Name",
                Boolean.TRUE,
                null, // null room id
                1L
        );

        mockMvc.perform(post(apiPath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.computerRoomId").value("Computer Room Id is mandatory"));

        verify(pcService, never()).save(any(PcDto.class));
    }

    /* PUT /computer/{id} tests */
    @Test
    void updateValid() throws Exception {
        when(pcService.update(eq(1L), any(PcDto.class))).thenReturn(pcEntity);
        PcDto partialDto = new PcDto(
                "Updated Pc Name",
                Boolean.FALSE,
                null,
                null
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partialDto)))
                .andExpect(status().isOk());

        verify(pcService, times(1)).update(eq(1L), any(PcDto.class));
    }

    @Test
    void updateInvalid_NameTooShort() throws Exception {
        PcDto invalidPartialDto = new PcDto(
                "-", // too short name
                Boolean.FALSE,
                null,
                null
        );

        mockMvc.perform(put(apiPath.concat("/1"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPartialDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Name must be between 2 and 100 characters"));

        verify(pcService, never()).update(eq(1L), any(PcDto.class));
    }

    /* GET /computer tests */
    @Test
    void getAllPcs() throws Exception {
        when(pcService.getAll()).thenReturn(List.of(pcEntity));

        mockMvc.perform(get(apiPath))
                .andExpect(status().isOk());

        verify(pcService, times(1)).getAll();
    }

    /* GET /computer?computerRoomId= tests */
    @Test
    void getPcsByRoom() throws Exception {
        when(roomService.getById(1L)).thenReturn(roomEntity);
        when(pcService.getByRoom(roomEntity)).thenReturn(List.of(pcEntity));

        mockMvc.perform(get(apiPath).param("computerRoomId", "1"))
                .andExpect(status().isOk());

        verify(roomService, times(1)).getById(1L);
        verify(pcService, times(1)).getByRoom(roomEntity);
    }

    /* GET /computer/{id} tests */
    @Test
    void getPcById() throws Exception {
        when(pcService.getById(1L)).thenReturn(pcEntity);

        mockMvc.perform(get(apiPath.concat("/1")))
                .andExpect(status().isOk());

        verify(pcService, times(1)).getById(1L);
    }

    /* GET /computer/{id}?unwrap=true tests */
    @Test
    void getPcByIdUnwrap() throws Exception {
        when(pcService.getById(1L)).thenReturn(pcEntity);

        mockMvc.perform(get(apiPath.concat("/1")).param("unwrap", "true"))
                .andExpect(status().isOk());

        // todo test the output later

        verify(pcService, times(1)).getById(1L);
    }

    /* DELETE /computer/{id} tests */
    @Test
    void deletePc() throws Exception {
        doNothing().when(pcService).delete(1L);

        mockMvc.perform(delete(apiPath.concat("/1")))
                .andExpect(status().isNoContent());

        verify(pcService, times(1)).delete(1L);
    }
}
