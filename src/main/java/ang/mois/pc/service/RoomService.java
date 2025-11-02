package ang.mois.pc.service;

import ang.mois.pc.dto.request.RoomRequestDto;
import ang.mois.pc.entity.Faculty;
import ang.mois.pc.entity.Room;
import ang.mois.pc.repository.FacultyRepository;
import ang.mois.pc.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final FacultyRepository facultyRepository;

    public RoomService(RoomRepository roomRepository, FacultyRepository facultyRepository) {
        this.roomRepository = roomRepository;
        this.facultyRepository = facultyRepository;
    }

    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public Room getById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room with id " + id + " does not exist"));
    }

    public List<Room> getByFaculty(Long facultyId) {
        Optional<Faculty> faculty = facultyRepository.findById(facultyId);
        if (faculty.isEmpty()) {
            throw new IllegalArgumentException("Faculty with id " + facultyId + " does not exist");
        }
        return roomRepository.findByFaculty(faculty.get());
    }

    public Room save(RoomRequestDto createRoomRequestDto) {
        Optional<Faculty> faculty = facultyRepository.findById(createRoomRequestDto.facultyId());
        if (faculty.isEmpty()) {
            throw new IllegalArgumentException("Faculty with id " + createRoomRequestDto.facultyId() + " does not exist");
        }
        Room room = new Room(createRoomRequestDto.name(), faculty.get());
        return roomRepository.save(room);
    }

    public void delete(Long id) {
        roomRepository.deleteById(id);
    }

    public Room update(Long idR, RoomRequestDto roomRequestDto) {
        Room room = getById(idR);
        if (roomRequestDto.name() != null) room.setName(roomRequestDto.name());
        return roomRepository.save(room);
    }
}