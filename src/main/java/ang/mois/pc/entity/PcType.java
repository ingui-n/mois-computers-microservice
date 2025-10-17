package ang.mois.pc.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "pcTypes")
public class PcType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cpu;
    private String memory;
    private String gpu;
    private String os;

    @OneToMany(mappedBy = "pcType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pc> pcs;

    public PcType(String name, String cpu, String memory, String gpu, String os) {
        this.name = name;
        this.cpu = cpu;
        this.memory = memory;
        this.gpu = gpu;
        this.os = os;
    }

    public PcType() {
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

    public List<Pc> getPcs() {
        return pcs;
    }

    public void setPcs(List<Pc> pcs) {
        this.pcs = pcs;
    }
}
