package org.example.gestionacademic.service;

import org.example.gestionacademic.dto.BulletinDTO;
import org.example.gestionacademic.dto.BulletinMatiereDTO;
import org.example.gestionacademic.entity.Note;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.AbsenceRepository;
import org.example.gestionacademic.repository.NoteRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BulletinService {

    private final StudentRepository studentRepository;
    private final NoteRepository noteRepository;
    private final AbsenceRepository absenceRepository;

    public BulletinService(
            StudentRepository studentRepository,
            NoteRepository noteRepository,
            AbsenceRepository absenceRepository
    ) {
        this.studentRepository = studentRepository;
        this.noteRepository = noteRepository;
        this.absenceRepository = absenceRepository;
    }

    public BulletinDTO getBulletinByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable avec id : " + studentId));

        List<Note> notes = noteRepository.findByStudentId(studentId);

        double totalPoints = 0;
        double totalCoefficients = 0;

        List<BulletinMatiereDTO> noteDtos = new ArrayList<>();

        for (Note note : notes) {
            double coefficient = note.getMatiere().getCoefficient() != null
                    ? note.getMatiere().getCoefficient()
                    : 1;

            totalPoints += note.getValeur() * coefficient;
            totalCoefficients += coefficient;

            noteDtos.add(new BulletinMatiereDTO(
                    note.getMatiere().getNom(),
                    coefficient,
                    note.getValeur(),
                    note.getTypeEvaluation()
            ));
        }

        double moyenne = 0;

        if (totalCoefficients > 0) {
            moyenne = totalPoints / totalCoefficients;
            moyenne = Math.round(moyenne * 100.0) / 100.0;
        }

        String resultat = moyenne >= 10 ? "Admis" : "Ajourné";

        long totalAbsences = absenceRepository.countByStudentId(studentId);

        BulletinDTO bulletin = new BulletinDTO();

        bulletin.setStudentId(student.getId());
        bulletin.setNomComplet(student.getFirstName() + " " + student.getLastName());
        bulletin.setCin(student.getCin());
        bulletin.setIdentifiant(student.getStudentIdentifier());
        bulletin.setEmail(student.getEmail());

        if (student.getFiliere() != null) {
            bulletin.setFiliere(student.getFiliere().getNom());
        } else {
            bulletin.setFiliere("Non renseignée");
        }

        bulletin.setNiveau(student.getStudyLevel());
        bulletin.setMoyenne(moyenne);
        bulletin.setResultat(resultat);
        bulletin.setTotalAbsences(totalAbsences);
        bulletin.setNotes(noteDtos);

        return bulletin;
    }
}