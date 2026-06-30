package org.example.gestionacademic.dto;

public class DashboardStatsDTO {

    private long totalStudents;
    private long totalFilieres;
    private long totalMatieres;
    private long totalNotes;
    private long totalAbsences;
    private double moyenneGenerale;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(
            long totalStudents,
            long totalFilieres,
            long totalMatieres,
            long totalNotes,
            long totalAbsences,
            double moyenneGenerale
    ) {
        this.totalStudents = totalStudents;
        this.totalFilieres = totalFilieres;
        this.totalMatieres = totalMatieres;
        this.totalNotes = totalNotes;
        this.totalAbsences = totalAbsences;
        this.moyenneGenerale = moyenneGenerale;
    }

    public long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public long getTotalFilieres() {
        return totalFilieres;
    }

    public void setTotalFilieres(long totalFilieres) {
        this.totalFilieres = totalFilieres;
    }

    public long getTotalMatieres() {
        return totalMatieres;
    }

    public void setTotalMatieres(long totalMatieres) {
        this.totalMatieres = totalMatieres;
    }

    public long getTotalNotes() {
        return totalNotes;
    }

    public void setTotalNotes(long totalNotes) {
        this.totalNotes = totalNotes;
    }

    public long getTotalAbsences() {
        return totalAbsences;
    }

    public void setTotalAbsences(long totalAbsences) {
        this.totalAbsences = totalAbsences;
    }

    public double getMoyenneGenerale() {
        return moyenneGenerale;
    }

    public void setMoyenneGenerale(double moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }
}