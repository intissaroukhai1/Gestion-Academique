package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByStudentId(Long studentId);
    @Query("SELECT COALESCE(AVG(n.valeur), 0) FROM Note n")
    double getMoyenneGenerale();
}