package org.example.gestionacademic.dto;

import java.util.List;

public class BulletinDTO {

    private Long studentId;
    private String nomComplet;
    private String cin;
    private String identifiant;
    private String email;
    private String filiere;
    private String niveau;
    private Double moyenne;
    private String resultat;
    private Long totalAbsences;
    private List<BulletinMatiereDTO> notes;

    public BulletinDTO() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Double moyenne) {
        this.moyenne = moyenne;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    public Long getTotalAbsences() {
        return totalAbsences;
    }

    public void setTotalAbsences(Long totalAbsences) {
        this.totalAbsences = totalAbsences;
    }

    public List<BulletinMatiereDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<BulletinMatiereDTO> notes) {
        this.notes = notes;
    }
}