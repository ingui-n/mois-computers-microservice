package ang.mois.pc.controller;

import ang.mois.pc.dto.PcFullDocument;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.service.PcService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pc")
public class PcController {
    private final PcService pcService;

    public PcController(PcService pcService) {
        this.pcService = pcService;
    }

    @GetMapping
    public ResponseEntity<List<Pc>> getAll() {
        return ResponseEntity.ok(pcService.getAllPcs());
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Pc>> getByRoom(@PathVariable String roomId) {
        return ResponseEntity.ok(pcService.getPcsByRoom(roomId));
    }

    @GetMapping("/type/{typeId}")
    public ResponseEntity<List<Pc>> getByType(@PathVariable String typeId) {
        return ResponseEntity.ok(pcService.getPcsByType(typeId));
    }

    @GetMapping("/full")
    public ResponseEntity<List<PcFullDocument>> getAllWithDetails() {
        return ResponseEntity.ok(pcService.getAllPcsWithDetails());
    }

    @GetMapping("/faculty/{faculty}")
    public ResponseEntity<List<PcFullDocument>> getAllByFacultyWithDetails(@PathVariable String faculty) {
        return ResponseEntity.ok(pcService.getAllPcsByFacultyWithDetails(faculty));
    }

    @PostMapping
    public ResponseEntity<Pc> addPc(@RequestBody Pc pc) {
        // todo validate input
        Pc saved = pcService.savePc(pc);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePc(@PathVariable String id) {
        pcService.deletePc(id);
        return ResponseEntity.noContent().build();
    }
}
