package ang.mois.pc.service;

import ang.mois.pc.dto.PcFullDocument;
import ang.mois.pc.entity.Pc;
import ang.mois.pc.entity.PcType;
import ang.mois.pc.entity.Room;
import ang.mois.pc.repository.PcRepository;
import ang.mois.pc.repository.PcTypeRepository;
import ang.mois.pc.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<PcFullDocument> getAllPcsWithDetails() {
        List<Pc> pcs = pcRepository.findAll();
        return pcs.stream().map(pc ->
                new PcFullDocument(
                        pc.getId(),
                        pc.getStatus(),
                        pcTypeRepository.findById(pc.getTypeId()).orElse(null),
                        roomRepository.findById(pc.getRoomId()).orElse(null)
                )).toList();
    }

    public List<PcFullDocument> getAllPcsByFacultyWithDetails(String faculty) {
        List<Room> rooms = roomRepository.findByFaculty(faculty);
        List<Pc> pcs = pcRepository.findByRoomIdIsIn(rooms.stream().map(Room::getId).toList());
        return pcs.stream().map(pc ->
                new PcFullDocument(
                        pc.getId(),
                        pc.getStatus(),
                        pcTypeRepository.findById(pc.getTypeId()).orElse(null),
                        roomRepository.findById(pc.getRoomId()).orElse(null)
                )).toList();
    }

    public List<PcFullDocument> getAllPcsByTypeWithDetails(PcType pcType) {
        List<Pc> pcs = pcRepository.findByTypeId(pcType.getId());
        return pcs.stream().map(pc ->
                new PcFullDocument(
                        pc.getId(),
                        pc.getStatus(),
                        pcType,
                        roomRepository.findById(pc.getRoomId()).orElse(null)
                )).toList();
    }

    public Pc savePc(Pc pc) {
        if(!roomRepository.existsById(pc.getRoomId())){
            throw new IllegalArgumentException("Room with id " + pc.getRoomId() + " does not exist");
        }
        if(!pcTypeRepository.existsById(pc.getTypeId())){
            throw new IllegalArgumentException("PcType with id " + pc.getTypeId() + " does not exist");
        }
        return pcRepository.save(pc);
    }

    public void deletePc(String id) {
        // todo add the logic on when the pc can be deleted
        pcRepository.deleteById(id);
    }

    public List<Pc> getAllPcs() {
        return pcRepository.findAll();
    }

    public List<Pc> getPcsByRoom(String roomId) {
        return pcRepository.findByRoomId(roomId);
    }

    public List<Pc> getPcsByType(String typeId) {
        return pcRepository.findByTypeId(typeId);
    }
}
