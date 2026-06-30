package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Absence;
import org.example.gestionacademic.entity.Matiere;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.AbsenceRepository;
import org.example.gestionacademic.repository.MatiereRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final StudentRepository studentRepository;
    private final MatiereRepository matiereRepository;

    public AbsenceService(
            AbsenceRepository absenceRepository,
            StudentRepository studentRepository,
            MatiereRepository matiereRepository
    ) {
        this.absenceRepository = absenceRepository;
        this.studentRepository = studentRepository;
        this.matiereRepository = matiereRepository;
    }

    public List<Absence> getAllAbsences() {
        return absenceRepository.findAll();
    }

    public Absence getAbsenceById(Long id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence introuvable avec id : " + id));
    }

    public Absence createAbsence(Absence absence) {
        Student student = studentRepository.findById(absence.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Matiere matiere = matiereRepository.findById(absence.getMatiere().getId())
                .orElseThrow(() -> new RuntimeException("Matière introuvable"));

        absence.setStudent(student);
        absence.setMatiere(matiere);

        return absenceRepository.save(absence);
    }

    public Absence updateAbsence(Long id, Absence absence) {
        Absence existing = getAbsenceById(id);

        Student student = studentRepository.findById(absence.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Matiere matiere = matiereRepository.findById(absence.getMatiere().getId())
                .orElseThrow(() -> new RuntimeException("Matière introuvable"));

        existing.setDateAbsence(absence.getDateAbsence());
        existing.setJustifiee(absence.getJustifiee());
        existing.setMotif(absence.getMotif());
        existing.setStudent(student);
        existing.setMatiere(matiere);

        return absenceRepository.save(existing);
    }

    public void deleteAbsence(Long id) {
        Absence absence = getAbsenceById(id);
        absenceRepository.delete(absence);
    }
}