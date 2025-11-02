package ang.mois.pc.controller;

import ang.mois.pc.dto.request.RoomRequestDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.FacultyService;
import ang.mois.pc.service.RoomService;
import ang.mois.pc.validation.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/computerRoom")
public class RoomController {

    private final RoomService roomService;
    private final FacultyService facultyService;

    @Autowired
    public RoomController(RoomService roomService, FacultyService facultyService) {
        this.roomService = roomService;
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping(params="facultyId")
    public ResponseEntity<List<Room>> getAll(@RequestParam(name = "facultyId") Long facultyId) {
        Faculty faculty = facultyService.getById(facultyId);
        return ResponseEntity.ok(roomService.getByFaculty(faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@Validated(ValidationGroups.OnCreate.class) @RequestBody RoomRequestDto roomRequestDto) {
        Room saved = roomService.save(roomRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @Validated @RequestBody RoomRequestDto roomRequestDto) {
        Room updated = roomService.update(id, roomRequestDto);
        return ResponseEntity.ok(updated);
    }
}
