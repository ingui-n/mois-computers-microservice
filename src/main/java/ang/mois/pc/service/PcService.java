package ang.mois.pc.service;

import ang.mois.pc.dto.request.PcRequestDto;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
import ang.mois.pc.mapper.PcMapper;
import ang.mois.pc.repository.PcRepository;
import ang.mois.pc.repository.PcTypeRepository;
import ang.mois.pc.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PcService {
    private final PcRepository pcRepository;
    private final RoomRepository roomRepository;
    private final PcTypeRepository pcTypeRepository;
    private final PcMapper pcMapper;

    @Autowired
    public PcService(PcRepository pcRepository, RoomRepository roomRepository, PcTypeRepository pcTypeRepository, PcMapper pcMapper) {
        this.pcRepository = pcRepository;
        this.roomRepository = roomRepository;
        this.pcTypeRepository = pcTypeRepository;
        this.pcMapper = pcMapper;
    }

    public List<Pc> getAll() {
        return pcRepository.findAll();
    }

    public Pc getById(Long id) {
        return pcRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Pc with id " + id + " does not exist"));
    }

    public Pc save(PcRequestDto pcRequestDto) {
        // retrieve foreign key entities and verify relation
        Room room = getRoom(pcRequestDto.computerRoomId());
        PcType type = getType(pcRequestDto.configId());

        // map basic properties
        Pc pc = pcMapper.toEntity(pcRequestDto);

        // set FK entities
        pc.setRoom(room);
        pc.setPcType(type);

        return pcRepository.save(pc);
    }

    public Pc update(Long id, PcRequestDto updatePcRequestDto) {
        Pc pc = getById(id);
        // verify foreign key relations
        if(updatePcRequestDto.computerRoomId() != null) {
            Room room = getRoom(updatePcRequestDto.computerRoomId());
            pc.setRoom(room);
        }

        if(updatePcRequestDto.configId() != null) {
            PcType type = getType(updatePcRequestDto.configId());
            pc.setPcType(type);
        }
        // merge entities
        pcMapper.updateEntityFromDto(updatePcRequestDto, pc);

        return pcRepository.save(pc);
    }

    private Room getRoom(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if(room.isEmpty()){
            throw new IllegalArgumentException("Room with id " + roomId + " does not exist");
        }
        return room.get();
    }

    private PcType getType(Long typeId) {
        Optional<PcType> type = pcTypeRepository.findById(typeId);
        if(type.isEmpty()){
            throw new IllegalArgumentException("PcType with id " + typeId + " does not exist");
        }
        return type.get();
    }

    public void delete(Long id) {
        pcRepository.deleteById(id);
    }

    public List<Pc> getByRoom(Room room) {
        return pcRepository.findByRoom(room);
    }

    public List<Pc> getByType(PcType type) {
        return pcRepository.findByPcType(type);
    }
}