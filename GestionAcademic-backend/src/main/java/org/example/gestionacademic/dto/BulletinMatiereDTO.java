package org.example.gestionacademic.dto;

public class BulletinMatiereDTO {

    private String matiere;
    private Double coefficient;
    private Double note;
    private String typeEvaluation;

    public BulletinMatiereDTO() {
    }

    public BulletinMatiereDTO(String matiere, Double coefficient, Double note, String typeEvaluation) {
        this.matiere = matiere;
        this.coefficient = coefficient;
        this.note = note;
        this.typeEvaluation = typeEvaluation;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public String getTypeEvaluation() {
        return typeEvaluation;
    }

    public void setTypeEvaluation(String typeEvaluation) {
        this.typeEvaluation = typeEvaluation;
    }
}