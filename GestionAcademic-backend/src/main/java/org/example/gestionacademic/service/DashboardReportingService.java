package org.example.gestionacademic.service;

import org.example.gestionacademic.dto.*;
import org.example.gestionacademic.entity.Facture;
import org.example.gestionacademic.entity.Note;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardReportingService {

    private final StudentRepository studentRepository;
    private final FiliereRepository filiereRepository;
    private final MatiereRepository matiereRepository;
    private final NoteRepository noteRepository;
    private final AbsenceRepository absenceRepository;
    private final PaiementRepository paiementRepository;
    private final FactureRepository factureRepository;

    public DashboardReportingService(
            StudentRepository studentRepository,
            FiliereRepository filiereRepository,
            MatiereRepository matiereRepository,
            NoteRepository noteRepository,
            AbsenceRepository absenceRepository,
            PaiementRepository paiementRepository,
            FactureRepository factureRepository
    ) {
        this.studentRepository = studentRepository;
        this.filiereRepository = filiereRepository;
        this.matiereRepository = matiereRepository;
        this.noteRepository = noteRepository;
        this.absenceRepository = absenceRepository;
        this.paiementRepository = paiementRepository;
        this.factureRepository = factureRepository;
    }

    public DashboardReportingDTO getReporting() {
        DashboardReportingDTO dto = new DashboardReportingDTO();

        dto.setTotalStudents(studentRepository.count());
        dto.setTotalFilieres(filiereRepository.count());
        dto.setTotalMatieres(matiereRepository.count());
        dto.setTotalNotes(noteRepository.count());
        dto.setTotalAbsences(absenceRepository.count());

        dto.setTotalPaiements(paiementRepository.count());

        dto.setFacturesPayees(factureRepository.countByStatutPaiement("PAYEE"));
        dto.setFacturesNonPayees(factureRepository.countByStatutPaiement("NON_PAYEE"));
        dto.setFacturesPartielles(factureRepository.countByStatutPaiement("PARTIELLE"));

        dto.setMontantTotalPaye(calculateMontantTotalPaye());
        dto.setMontantRestant(calculateMontantRestant());

        dto.setMoyenneGenerale(calculateMoyenneGenerale());
        dto.setTauxReussite(calculateTauxReussite());

        dto.setStudentsByFiliere(getStudentsByFiliere());
        dto.setMoyenneByFiliere(getMoyenneByFiliere());
        dto.setAbsencesByMatiere(getAbsencesByMatiere());
        dto.setProgressionAcademique(getProgressionAcademique());

        return dto;
    }

    private double calculateMoyenneGenerale() {
        List<Note> notes = noteRepository.findAll();

        if (notes.isEmpty()) {
            return 0;
        }

        double somme = 0;

        for (Note note : notes) {
            somme += note.getValeur();
        }

        double moyenne = somme / notes.size();

        return Math.round(moyenne * 100.0) / 100.0;
    }

    private double calculateTauxReussite() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            return 0;
        }

        int admis = 0;

        for (Student student : students) {
            List<Note> notes = noteRepository.findByStudentId(student.getId());

            if (notes.isEmpty()) {
                continue;
            }

            double somme = 0;

            for (Note note : notes) {
                somme += note.getValeur();
            }

            double moyenne = somme / notes.size();

            if (moyenne >= 10) {
                admis++;
            }
        }

        double taux = ((double) admis / students.size()) * 100;

        return Math.round(taux * 100.0) / 100.0;
    }

    private List<StatsByFiliereDTO> getStudentsByFiliere() {
        List<StatsByFiliereDTO> result = new ArrayList<>();

        filiereRepository.findAll().forEach(filiere -> {
            long total = studentRepository.findAll()
                    .stream()
                    .filter(student ->
                            student.getFiliere() != null
                                    && student.getFiliere().getId().equals(filiere.getId())
                    )
                    .count();

            result.add(new StatsByFiliereDTO(filiere.getNom(), total));
        });

        return result;
    }

    private List<MoyenneByFiliereDTO> getMoyenneByFiliere() {
        List<MoyenneByFiliereDTO> result = new ArrayList<>();

        filiereRepository.findAll().forEach(filiere -> {
            List<Student> students = studentRepository.findAll()
                    .stream()
                    .filter(student ->
                            student.getFiliere() != null
                                    && student.getFiliere().getId().equals(filiere.getId())
                    )
                    .toList();

            double somme = 0;
            int count = 0;

            for (Student student : students) {
                List<Note> notes = noteRepository.findByStudentId(student.getId());

                for (Note note : notes) {
                    somme += note.getValeur();
                    count++;
                }
            }

            double moyenne = count == 0 ? 0 : somme / count;
            moyenne = Math.round(moyenne * 100.0) / 100.0;

            result.add(new MoyenneByFiliereDTO(filiere.getNom(), moyenne));
        });

        return result;
    }

    private List<AbsenceByMatiereDTO> getAbsencesByMatiere() {
        List<AbsenceByMatiereDTO> result = new ArrayList<>();

        matiereRepository.findAll().forEach(matiere -> {
            long total = absenceRepository.findAll()
                    .stream()
                    .filter(absence ->
                            absence.getMatiere() != null
                                    && absence.getMatiere().getId().equals(matiere.getId())
                    )
                    .count();

            result.add(new AbsenceByMatiereDTO(matiere.getNom(), total));
        });

        return result;
    }

    private List<ProgressionStudentDTO> getProgressionAcademique() {
        List<ProgressionStudentDTO> result = new ArrayList<>();

        for (Student student : studentRepository.findAll()) {
            List<Note> notes = noteRepository.findByStudentId(student.getId());

            double moyenne = 0;

            if (!notes.isEmpty()) {
                double somme = 0;

                for (Note note : notes) {
                    somme += note.getValeur();
                }

                moyenne = somme / notes.size();
                moyenne = Math.round(moyenne * 100.0) / 100.0;
            }

            String resultat = moyenne >= 10 ? "Admis" : "Ajourné";

            result.add(new ProgressionStudentDTO(
                    student.getFirstName() + " " + student.getLastName(),
                    moyenne,
                    resultat
            ));
        }

        return result;
    }

    private double calculateMontantTotalPaye() {
        BigDecimal total = paiementRepository.getTotalPaiements();

        if (total == null) {
            return 0;
        }

        return Math.round(total.doubleValue() * 100.0) / 100.0;
    }

    private double calculateMontantRestant() {
        double totalRestant = 0;

        for (Facture facture : factureRepository.findAll()) {
            BigDecimal totalPaid = paiementRepository.getTotalPaidByFactureId(facture.getId());

            if (totalPaid == null) {
                totalPaid = BigDecimal.ZERO;
            }

            BigDecimal restant = facture.getMontant().subtract(totalPaid);

            if (restant.compareTo(BigDecimal.ZERO) > 0) {
                totalRestant += restant.doubleValue();
            }
        }

        return Math.round(totalRestant * 100.0) / 100.0;
    }
}