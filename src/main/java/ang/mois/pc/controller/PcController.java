package ang.mois.pc.controller;

import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.PcService;
import ang.mois.pc.service.PcTypeService;
import ang.mois.pc.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pc")
public class PcController {
    private final PcService pcService;
    private final RoomService roomService;
    private final PcTypeService pcTypeService;

    public PcController(PcService pcService, RoomService roomService, PcTypeService pcTypeService) {
        this.pcService = pcService;
        this.roomService = roomService;
        this.pcTypeService = pcTypeService;
    }

    @GetMapping
    public ResponseEntity<List<Pc>> getAll() {
        return ResponseEntity.ok(pcService.getAll());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Pc>> getByRoom(@PathVariable Long roomId) {
        Room room = roomService.getById(roomId);
        return ResponseEntity.ok(pcService.getByRoom(room));
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<Pc>> getByType(@PathVariable Long typeId) {
        PcType pcType = pcTypeService.getById(typeId);
        return ResponseEntity.ok(pcService.getByType(pcType));
    }

    @PostMapping
    public ResponseEntity<Pc> addPc(@RequestBody Pc pc) {
        Pc saved = pcService.save(pc);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePc(@PathVariable Long id) {
        pcService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
