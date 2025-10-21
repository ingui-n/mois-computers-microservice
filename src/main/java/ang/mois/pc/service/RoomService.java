package ang.mois.pc.service;

import ang.mois.pc.dto.CreateRoomDto;
import ang.mois.pc.dto.RoomDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import ang.mois.pc.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final FacultyService facultyService;

    public RoomService(RoomRepository roomRepository, FacultyService facultyService) {
        this.roomRepository = roomRepository;
        this.facultyService = facultyService;
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

    public Room save(CreateRoomDto createRoomDto) {
        Faculty faculty = facultyService.getById(createRoomDto.facultyId());
        Room room = new Room(createRoomDto.name(), faculty);
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