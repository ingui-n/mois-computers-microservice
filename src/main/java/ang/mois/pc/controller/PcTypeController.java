package ang.mois.pc.controller;

import ang.mois.pc.entity.PcType;
import ang.mois.pc.service.PcTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class PcTypeController {
    private final PcTypeService pcTypeService;

    @Autowired
    public PcTypeController(PcTypeService pcTypeService) {
        this.pcTypeService = pcTypeService;
    }

    @GetMapping
    public ResponseEntity<List<PcType>> getAll() {
        return ResponseEntity.ok(pcTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PcType> getType(@PathVariable Long id) {
        return ResponseEntity.ok(pcTypeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<PcType> addType(@RequestBody PcType type) {
        // todo validate input
        PcType saved = pcTypeService.save(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        pcTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PcType> updateRoom(@PathVariable Long id, @RequestBody PcType pcType) {
        // todo validate input
        pcType.setId(id);

        PcType updated = pcTypeService.save(pcType);
        return ResponseEntity.ok(updated);
    }
}
