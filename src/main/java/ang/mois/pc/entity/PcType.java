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
    private String ram;
    private String gpu;

    @OneToMany(mappedBy = "pcType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pc> pcs;

    public PcType(String name, String cpu, String ram, String gpu) {
        this.name = name;
        this.cpu = cpu;
        this.ram = ram;
        this.gpu = gpu;
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

    public String getRam() {
        return ram;
    }

    public void setRam(String memory) {
        this.ram = memory;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public List<Pc> getPcs() {
        return pcs;
    }

    public void setPcs(List<Pc> pcs) {
        this.pcs = pcs;
    }
}
