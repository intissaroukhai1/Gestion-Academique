package org.example.gestionacademic.dto;

public class AbsenceByMatiereDTO {

    private String matiere;
    private long total;

    public AbsenceByMatiereDTO(String matiere, long total) {
        this.matiere = matiere;
        this.total = total;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}