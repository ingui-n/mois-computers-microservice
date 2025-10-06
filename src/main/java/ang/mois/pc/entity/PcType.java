package ang.mois.pc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pcTypes")
public class PcType {
    @Id
    private String id;
    private String name;
    private String cpu;
    private String memory;
    private String gpu;
    private String os;

    public PcType(String name, String cpu, String memory, String gpu, String os) {
        this.name = name;
        this.cpu = cpu;
        this.memory = memory;
        this.gpu = gpu;
        this.os = os;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
}
