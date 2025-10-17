package ang.mois.pc.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pcs")
public class Pc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "pc_type_id", nullable = false)
    private PcType pcType;

    public Pc(Status status, Room room, PcType pcType) {
        this.status = status;
        this.room = room;
        this.pcType = pcType;
        this.createdAt = LocalDateTime.now();
    }

    public Pc() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
