package ang.mois.pc.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "pcs")
public class Pc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;   // ok | broken | unavailable
    // Each PC belongs to one room
    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    // Each PC belongs to one pcType
    @ManyToOne
    @JoinColumn(name = "pc_type_id", nullable = false)
    private PcType pcType;

    public Pc(String status, Room room, PcType pcType) {
        this.status = status;
        this.room = room;
        this.pcType = pcType;
    }

    public Pc() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public PcType getPcType() {
        return pcType;
    }

    public void setPcType(PcType pcType) {
        this.pcType = pcType;
    }
}
