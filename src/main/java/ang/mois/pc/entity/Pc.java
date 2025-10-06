package ang.mois.pc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pcs")
public class Pc {
    @Id
    private String id;
    private String status;   // ok | broken | unavailable
    private String typeId;   // reference to pcTypes
    private String roomId;   // reference to rooms

    public Pc(String status, String typeId, String roomId) {
        this.status = status;
        this.typeId = typeId;
        this.roomId = roomId;
    }

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
