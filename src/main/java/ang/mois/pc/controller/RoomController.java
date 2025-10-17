package ang.mois.pc.controller;

import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import ang.mois.pc.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/faculty/{faculty}")
    public ResponseEntity<List<Room>> getRoomsByFaculty(@PathVariable String faculty) {
        Faculty facultyKey;
        try{
            facultyKey = Faculty.valueOf(faculty);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(roomService.getByFaculty(facultyKey));
    }

    @GetMapping("/faculty")
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        return ResponseEntity.ok(Arrays.stream(Faculty.values()).toList());
    }

    @GetMapping
    public ResponseEntity<List<Room>> getAll() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        // todo validate input
        Room saved = roomService.save(room);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        // todo validate input
        room.setId(id);
        Room updated = roomService.save(room);
        return ResponseEntity.ok(updated);
    }
}
