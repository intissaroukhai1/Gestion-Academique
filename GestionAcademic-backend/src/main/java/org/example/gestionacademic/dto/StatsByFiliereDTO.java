package org.example.gestionacademic.dto;

public class StatsByFiliereDTO {

    private String filiere;
    private long total;

    public StatsByFiliereDTO(String filiere, long total) {
        this.filiere = filiere;
        this.total = total;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}