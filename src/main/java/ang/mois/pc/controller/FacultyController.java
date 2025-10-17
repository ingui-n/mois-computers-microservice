package ang.mois.pc.controller;

import ang.mois.pc.dto.FacultyDto;
import ang.mois.pc.dto.RoomDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.FacultyService;
import ang.mois.pc.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;
    private final RoomService roomService;

    public FacultyController(FacultyService facultyService, RoomService roomService) {
        this.facultyService = facultyService;
        this.roomService = roomService;
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
    public ResponseEntity<Faculty> addFaculty(@RequestBody FacultyDto facultyDto) {
        return ResponseEntity.ok(facultyService.save(facultyDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        return ResponseEntity.ok(facultyService.update(id, facultyDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /* Rooms */
    @GetMapping("/faculty/{id}/computerRoom")
    public ResponseEntity<List<Room>> getRoomsByFaculty(@PathVariable Long id) {
        Faculty facultyKey = facultyService.getById(id);
        return ResponseEntity.ok(roomService.getByFaculty(facultyKey));
    }

    @GetMapping("/faculty/{idF}/computerRoom/{idR}")
    public ResponseEntity<Room> getRoomsById(@PathVariable Long idF, @PathVariable Long idR) {
        // note: faculty id is redundant here, maybe can be used in validations
        return ResponseEntity.ok(roomService.getById(idR));
    }

    @PostMapping("/faculty/{idF}/computerRoom")
    public ResponseEntity<Room> addRoom(@PathVariable Long idF, @RequestBody RoomDto roomDto) {
        Room room = new Room(roomDto.name(), facultyService.getById(idF));
        Room saved = roomService.save(room);
        return ResponseEntity.status(201).body(saved);
    }

    @PutMapping("/faculty/{idF}/computerRoom/{idR}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long idF, @PathVariable Long idR, @RequestBody RoomDto roomDto) {
        Room updated = roomService.update(idR, roomDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/faculty/{idF}/computerRoom/{idR}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long idF, @PathVariable Long idR) {
        roomService.delete(idR);
        return ResponseEntity.noContent().build();
    }
}
