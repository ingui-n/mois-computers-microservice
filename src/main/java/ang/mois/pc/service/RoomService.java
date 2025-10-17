package ang.mois.pc.service;

import ang.mois.pc.dto.RoomDto;
import ang.mois.pc.entity.Faculty;
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

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + id + " does not exist"));
    }

    public List<Room> getByFaculty(Faculty faculty) {
        return roomRepository.findByFaculty(faculty);
    }

    public Room save(Room room) {
        if (roomRepository.existsById(room.getId())) {
            throw new IllegalArgumentException("Room with id " + room.getId() + " already exists");
        }
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    public Room update(Long idR, RoomDto roomDto) {
        Room room = getById(idR);
        if (roomDto.name() != null) room.setName(roomDto.name());
        return roomRepository.save(room);
    }
}