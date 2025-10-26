package ang.mois.pc.service;

import ang.mois.pc.dto.CreatePcDto;
import ang.mois.pc.dto.UpdatePcDto;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
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

    @Autowired
    public PcService(PcRepository pcRepository, RoomRepository roomRepository, PcTypeRepository pcTypeRepository) {
        this.pcRepository = pcRepository;
        this.roomRepository = roomRepository;
        this.pcTypeRepository = pcTypeRepository;
    }

    public List<Pc> getAll() {
        return pcRepository.findAll();
    }

    public Pc getById(Long id) {
        return pcRepository.findById(id)
                .orElseThrow(() ->new IllegalArgumentException("Pc with id " + id + " does not exist"));
    }

    public Pc save(CreatePcDto createPcDto) {
        Optional<Room> room = roomRepository.findById(createPcDto.computerRoomId());
        if(room.isEmpty()){
            throw new IllegalArgumentException("Room with id " + createPcDto.computerRoomId() + " does not exist");
        }

        Optional<PcType> type = pcTypeRepository.findById(createPcDto.configId());
        if(type.isEmpty()){
            throw new IllegalArgumentException("PcType with id " + createPcDto.configId() + " does not exist");
        }

        Pc pc = new Pc(
            createPcDto.name(),
            createPcDto.available(),
            room.get(),
            type.get()
        );

        return pcRepository.save(pc);
    }

    public Pc update(Long id, UpdatePcDto updatePcDto) {
        Pc pc = getById(id);
        // todo merge etities
        if(updatePcDto.name() != null) pc.setName(updatePcDto.name());

        return pcRepository.save(pc);
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