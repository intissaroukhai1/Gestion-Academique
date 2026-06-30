package org.example.gestionacademic.service;

import org.example.gestionacademic.dto.DashboardStatsDTO;
import org.example.gestionacademic.repository.AbsenceRepository;
import org.example.gestionacademic.repository.FiliereRepository;
import org.example.gestionacademic.repository.MatiereRepository;
import org.example.gestionacademic.repository.NoteRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final FiliereRepository filiereRepository;
    private final MatiereRepository matiereRepository;
    private final NoteRepository noteRepository;
    private final AbsenceRepository absenceRepository;

    public DashboardService(
            StudentRepository studentRepository,
            FiliereRepository filiereRepository,
            MatiereRepository matiereRepository,
            NoteRepository noteRepository,
            AbsenceRepository absenceRepository
    ) {
        this.studentRepository = studentRepository;
        this.filiereRepository = filiereRepository;
        this.matiereRepository = matiereRepository;
        this.noteRepository = noteRepository;
        this.absenceRepository = absenceRepository;
    }

    public DashboardStatsDTO getStats() {
        long totalStudents = studentRepository.count();
        long totalFilieres = filiereRepository.count();
        long totalMatieres = matiereRepository.count();
        long totalNotes = noteRepository.count();
        long totalAbsences = absenceRepository.count();

        double moyenne = noteRepository.getMoyenneGenerale();
        moyenne = Math.round(moyenne * 100.0) / 100.0;

        return new DashboardStatsDTO(
                totalStudents,
                totalFilieres,
                totalMatieres,
                totalNotes,
                totalAbsences,
                moyenne
        );
    }
}