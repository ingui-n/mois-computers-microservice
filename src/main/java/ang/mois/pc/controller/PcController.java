package ang.mois.pc.controller;

import ang.mois.pc.dto.CreatePcDto;
import ang.mois.pc.dto.UpdatePcDto;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.PcService;
import ang.mois.pc.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Pc>> getAll() {
        return ResponseEntity.ok(pcService.getAll());
    }

    @GetMapping()
    public ResponseEntity<List<Pc>> getByRoom(@RequestParam(name="computerRoomId") Long computerRoomId) {
        Room room = roomService.getById(computerRoomId);
        return ResponseEntity.ok(pcService.getByRoom(room));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pc> getById(@PathVariable Long id) {
        return ResponseEntity.ok(pcService.getById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pc> getById(@PathVariable Long id, @RequestParam(name="configId") boolean unwrap) {
        // todo make unwrap
        return ResponseEntity.ok(pcService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Pc> addPc(@RequestBody CreatePcDto createPcDto) {
        Pc saved = pcService.save(createPcDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pc> updatePc(@PathVariable Long id, @RequestBody UpdatePcDto updatePcDto) {
        Pc updated = pcService.update(id, updatePcDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePc(@PathVariable Long id) {
        pcService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
