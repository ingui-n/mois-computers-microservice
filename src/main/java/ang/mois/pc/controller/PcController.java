package ang.mois.pc.controller;

import ang.mois.pc.dto.request.PcRequestDto;
import ang.mois.pc.dto.response.PcResponseDto;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.PcService;
import ang.mois.pc.service.RoomService;
import ang.mois.pc.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computer")
public class PcController {
    private final PcService pcService;
    private final RoomService roomService;

    public PcController(PcService pcService, RoomService roomService) {
        this.pcService = pcService;
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<List<PcResponseDto>> getAll() {
        return ResponseEntity.ok(pcService.getAll());
    }

    @GetMapping(params="computerRoomId")
    public ResponseEntity<List<PcResponseDto>> getByRoom(@RequestParam(name="computerRoomId") Long computerRoomId) {
        Room room = roomService.getById(computerRoomId);
        return ResponseEntity.ok(pcService.getByRoom(room));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PcResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pcService.getById(id));
    }

    @GetMapping(value="/{id}", params = "unwrap")
    public ResponseEntity<PcResponseDto> getById(@PathVariable Long id, @RequestParam(name="unwrap") boolean unwrap) {
        // todo make unwrap
        return ResponseEntity.ok(pcService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PcResponseDto> addPc(@Validated(ValidationGroups.OnCreate.class) @RequestBody PcRequestDto pcRequestDto) {
        PcResponseDto saved = pcService.save(pcRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PcResponseDto> updatePc(@PathVariable Long id, @Validated @RequestBody PcRequestDto pcRequestDto) {
        PcResponseDto updated = pcService.update(id, pcRequestDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePc(@PathVariable Long id) {
        pcService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
