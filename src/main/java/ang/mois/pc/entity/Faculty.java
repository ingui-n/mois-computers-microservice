package ang.mois.pc.entity;

public enum Faculty {
    FIM("Faculty of Informatics and Management"),
    PdF("Pedagogical Faculty"),
    PrF("Scientifical Faculty"),
    FF("Philosophical Faculty");

    private final String fullName;

    Faculty(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
