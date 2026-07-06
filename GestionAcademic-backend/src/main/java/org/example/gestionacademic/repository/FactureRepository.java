package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FactureRepository extends JpaRepository<Facture, Long> {

    Optional<Facture> findByNumeroFacture(String numeroFacture);

    List<Facture> findByStudentId(Long studentId);

    List<Facture> findByStatutPaiement(String statutPaiement);
    long countByStatutPaiement(String statutPaiement);
}