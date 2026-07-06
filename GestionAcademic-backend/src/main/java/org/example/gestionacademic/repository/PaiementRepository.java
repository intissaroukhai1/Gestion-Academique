package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {

    List<Paiement> findByStudentId(Long studentId);

    List<Paiement> findByFactureId(Long factureId);

    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p WHERE p.facture.id = :factureId")
    BigDecimal getTotalPaidByFactureId(@Param("factureId") Long factureId);

    @Query("SELECT COALESCE(SUM(p.montant), 0) FROM Paiement p")
    BigDecimal getTotalPaiements();
}