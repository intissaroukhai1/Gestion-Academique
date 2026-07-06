package org.example.gestionacademic.dto;

import java.util.List;

public class DashboardReportingDTO {

    private long totalStudents;
    private long totalFilieres;
    private long totalMatieres;
    private long totalNotes;
    private long totalAbsences;
    private long totalPaiements;

    private double moyenneGenerale;
    private double tauxReussite;
    private long facturesPayees;
    private long facturesNonPayees;
    private long facturesPartielles;

    private double montantTotalPaye;
    private double montantRestant;
    private List<StatsByFiliereDTO> studentsByFiliere;
    private List<MoyenneByFiliereDTO> moyenneByFiliere;
    private List<AbsenceByMatiereDTO> absencesByMatiere;
    private List<ProgressionStudentDTO> progressionAcademique;

    public DashboardReportingDTO() {}

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

    public long getTotalPaiements() {
        return totalPaiements;
    }

    public void setTotalPaiements(long totalPaiements) {
        this.totalPaiements = totalPaiements;
    }

    public double getMoyenneGenerale() {
        return moyenneGenerale;
    }

    public void setMoyenneGenerale(double moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    public double getTauxReussite() {
        return tauxReussite;
    }

    public void setTauxReussite(double tauxReussite) {
        this.tauxReussite = tauxReussite;
    }

    public List<StatsByFiliereDTO> getStudentsByFiliere() {
        return studentsByFiliere;
    }

    public void setStudentsByFiliere(List<StatsByFiliereDTO> studentsByFiliere) {
        this.studentsByFiliere = studentsByFiliere;
    }

    public List<MoyenneByFiliereDTO> getMoyenneByFiliere() {
        return moyenneByFiliere;
    }

    public void setMoyenneByFiliere(List<MoyenneByFiliereDTO> moyenneByFiliere) {
        this.moyenneByFiliere = moyenneByFiliere;
    }

    public List<AbsenceByMatiereDTO> getAbsencesByMatiere() {
        return absencesByMatiere;
    }

    public void setAbsencesByMatiere(List<AbsenceByMatiereDTO> absencesByMatiere) {
        this.absencesByMatiere = absencesByMatiere;
    }

    public List<ProgressionStudentDTO> getProgressionAcademique() {
        return progressionAcademique;
    }

    public void setProgressionAcademique(List<ProgressionStudentDTO> progressionAcademique) {
        this.progressionAcademique = progressionAcademique;
    }
    public long getFacturesPayees() {
        return facturesPayees;
    }

    public void setFacturesPayees(long facturesPayees) {
        this.facturesPayees = facturesPayees;
    }

    public long getFacturesNonPayees() {
        return facturesNonPayees;
    }

    public void setFacturesNonPayees(long facturesNonPayees) {
        this.facturesNonPayees = facturesNonPayees;
    }

    public long getFacturesPartielles() {
        return facturesPartielles;
    }

    public void setFacturesPartielles(long facturesPartielles) {
        this.facturesPartielles = facturesPartielles;
    }

    public double getMontantTotalPaye() {
        return montantTotalPaye;
    }

    public void setMontantTotalPaye(double montantTotalPaye) {
        this.montantTotalPaye = montantTotalPaye;
    }

    public double getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(double montantRestant) {
        this.montantRestant = montantRestant;
    }
}