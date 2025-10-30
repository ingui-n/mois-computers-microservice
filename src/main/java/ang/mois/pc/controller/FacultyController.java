package ang.mois.pc.controller;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.service.FacultyService;
import ang.mois.pc.validation.ValidationGroups;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAll() {
        return ResponseEntity.ok(facultyService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        return ResponseEntity.ok(facultyService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@Validated(ValidationGroups.OnCreate.class) @RequestBody FacultyDto facultyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.save(facultyDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @Validated @RequestBody FacultyDto facultyDto) {
        return ResponseEntity.ok(facultyService.update(id, facultyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
