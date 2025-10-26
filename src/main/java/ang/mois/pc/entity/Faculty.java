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
    private int maxUserReservationCount; // of all time
    private int maxUserReservationTime; // for 1 reservation in minutes
    private int maxUserReservationTimeWeekly; // weekly in minutes
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms;

    public Faculty(String name, String shortcut) {
        this.name = name;
        this.shortcut = shortcut;
        // default params
        this.createdAt = LocalDateTime.now();
        this.reservationTimeStart = Time.valueOf("08:00:00");
        this.reservationTimeEnd = Time.valueOf("20:00:00");
        this.maxUserReservationCount = 9999;
        this.maxUserReservationTime = 180;
        this.maxUserReservationTimeWeekly = 900;
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

    public Time getReservationTimeStart() {
        return reservationTimeStart;
    }

    public void setReservationTimeStart(Time reservationTimeStart) {
        this.reservationTimeStart = reservationTimeStart;
    }

    public Time getReservationTimeEnd() {
        return reservationTimeEnd;
    }

    public void setReservationTimeEnd(Time reservationTimeEnd) {
        this.reservationTimeEnd = reservationTimeEnd;
    }

    public int getMaxUserReservationCount() {
        return maxUserReservationCount;
    }

    public void setMaxUserReservationCount(int maxUserReservationCount) {
        this.maxUserReservationCount = maxUserReservationCount;
    }

    public int getMaxUserReservationTime() {
        return maxUserReservationTime;
    }

    public void setMaxUserReservationTime(int maxUserReservationTime) {
        this.maxUserReservationTime = maxUserReservationTime;
    }

    public int getMaxUserReservationTimeWeekly() {
        return maxUserReservationTimeWeekly;
    }

    public void setMaxUserReservationTimeWeekly(int maxUserReservationTimeWeekly) {
        this.maxUserReservationTimeWeekly = maxUserReservationTimeWeekly;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
