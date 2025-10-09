package ang.mois.pc.service;

import ang.mois.pc.entity.Room;
import ang.mois.pc.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room get(String id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + id + " does not exist"));
    }

    public List<Room> getRoomsByFaculty(String faculty) {
        return roomRepository.findByFaculty(faculty);
    }

    public Room saveRoom(Room room) {
        if (roomRepository.existsById(room.getId())) {
            throw new IllegalArgumentException("Room with id " + room.getId() + " already exists");
        }
        return roomRepository.save(room);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }
}