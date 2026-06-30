package org.example.gestionacademic.dto;

public class MoyenneByFiliereDTO {

    private String filiere;
    private double moyenne;

    public MoyenneByFiliereDTO(String filiere, double moyenne) {
        this.filiere = filiere;
        this.moyenne = moyenne;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }
}