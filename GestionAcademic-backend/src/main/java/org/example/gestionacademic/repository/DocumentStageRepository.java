package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.DocumentStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentStageRepository extends JpaRepository<DocumentStage, Long> {

    List<DocumentStage> findByStudentId(Long studentId);

    List<DocumentStage> findByStatut(String statut);

    List<DocumentStage> findByTypeDocument(String typeDocument);
}