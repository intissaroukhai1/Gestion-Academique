package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {

    long countByStudentId(Long studentId);
}