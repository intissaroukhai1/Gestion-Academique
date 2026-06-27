package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}