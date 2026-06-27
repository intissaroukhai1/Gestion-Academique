package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatiereRepository extends JpaRepository<Matiere, Long> {
}