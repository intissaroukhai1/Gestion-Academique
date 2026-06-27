package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Matiere;
import org.example.gestionacademic.entity.Note;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.MatiereRepository;
import org.example.gestionacademic.repository.NoteRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final StudentRepository studentRepository;
    private final MatiereRepository matiereRepository;

    public NoteService(
            NoteRepository noteRepository,
            StudentRepository studentRepository,
            MatiereRepository matiereRepository
    ) {
        this.noteRepository = noteRepository;
        this.studentRepository = studentRepository;
        this.matiereRepository = matiereRepository;
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note createNote(Note note) {
        Student student = studentRepository.findById(note.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Matiere matiere = matiereRepository.findById(note.getMatiere().getId())
                .orElseThrow(() -> new RuntimeException("Matière introuvable"));

        note.setStudent(student);
        note.setMatiere(matiere);

        return noteRepository.save(note);
    }

    public Note updateNote(Long id, Note note) {
        Note existing = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note introuvable"));

        Student student = studentRepository.findById(note.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Matiere matiere = matiereRepository.findById(note.getMatiere().getId())
                .orElseThrow(() -> new RuntimeException("Matière introuvable"));

        existing.setValeur(note.getValeur());
        existing.setTypeEvaluation(note.getTypeEvaluation());
        existing.setDateEvaluation(note.getDateEvaluation());
        existing.setStudent(student);
        existing.setMatiere(matiere);

        return noteRepository.save(existing);
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}