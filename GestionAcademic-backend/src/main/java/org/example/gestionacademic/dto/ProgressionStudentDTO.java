package org.example.gestionacademic.dto;

public class ProgressionStudentDTO {

    private String student;
    private double moyenne;
    private String resultat;

    public ProgressionStudentDTO(String student, double moyenne, String resultat) {
        this.student = student;
        this.moyenne = moyenne;
        this.resultat = resultat;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }
}