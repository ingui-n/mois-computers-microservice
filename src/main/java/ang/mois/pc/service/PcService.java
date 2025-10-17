package ang.mois.pc.service;

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
        Optional<Pc> pcOpt = pcRepository.findById(id);
        if(pcOpt.isEmpty()){
            throw new IllegalArgumentException("Pc with id " + id + " does not exist");
        }
        return pcOpt.get();
    }

    public Pc save(Pc pc) {
        if(!roomRepository.existsById(pc.getRoom().getId())){
            throw new IllegalArgumentException("Room with id " + pc.getRoom().getId() + " does not exist");
        }
        if(!pcTypeRepository.existsById(pc.getPcType().getId())){
            throw new IllegalArgumentException("PcType with id " + pc.getPcType().getId() + " does not exist");
        }
        return pcRepository.save(pc);
    }

    public void delete(Long id) {
        pcRepository.deleteById(id);
    }

    public List<Pc> getByRoom(Room room) {
        return pcRepository.findByRoom(room);
    }

    public List<Pc> getByRooms(List<Room> room) {
        return pcRepository.findAllByRoomIn(room);
    }


    public List<Pc> getByType(PcType type) {
        return pcRepository.findByPcType(type);
    }
}
