package ang.mois.pc.entity;

import jakarta.persistence.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortcut;
    private Time reservationTimeStart;
    private Time reservationTimeEnd;
    private int maxUserReservationCount; // ? of all time or what does this mean
    private int maxUserReservationTime; // for 1 reservation in minutes
    private int maxUserReservationTimeWeekly; // weekly in minutes
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    public Faculty(String name, String shortcut) {
        this.name = name;
        this.shortcut = shortcut;
        this.createdAt = LocalDateTime.now();
    }

    public Faculty() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
