package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Facture;
import org.example.gestionacademic.entity.Paiement;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.FactureRepository;
import org.example.gestionacademic.repository.PaiementRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final FactureRepository factureRepository;
    private final StudentRepository studentRepository;

    public PaiementService(
            PaiementRepository paiementRepository,
            FactureRepository factureRepository,
            StudentRepository studentRepository
    ) {
        this.paiementRepository = paiementRepository;
        this.factureRepository = factureRepository;
        this.studentRepository = studentRepository;
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    public Paiement getPaiementById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paiement introuvable avec id : " + id));
    }

    public List<Paiement> getPaiementsByStudent(Long studentId) {
        return paiementRepository.findByStudentId(studentId);
    }

    public List<Paiement> getPaiementsByFacture(Long factureId) {
        return paiementRepository.findByFactureId(factureId);
    }

    public Paiement createPaiement(Paiement paiement) {
        if (paiement.getFacture() == null || paiement.getFacture().getId() == null) {
            throw new RuntimeException("La facture est obligatoire");
        }

        if (paiement.getStudent() == null || paiement.getStudent().getId() == null) {
            throw new RuntimeException("L'étudiant est obligatoire");
        }

        Facture facture = factureRepository.findById(paiement.getFacture().getId())
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        Student student = studentRepository.findById(paiement.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        paiement.setFacture(facture);
        paiement.setStudent(student);

        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(LocalDate.now());
        }

        if (paiement.getModePaiement() == null || paiement.getModePaiement().isBlank()) {
            paiement.setModePaiement("ESPECE");
        }

        Paiement savedPaiement = paiementRepository.save(paiement);

        updateFactureStatus(facture.getId());

        return savedPaiement;
    }

    public Paiement updatePaiement(Long id, Paiement paiement) {
        Paiement existingPaiement = getPaiementById(id);

        Long oldFactureId = existingPaiement.getFacture().getId();

        if (paiement.getFacture() != null && paiement.getFacture().getId() != null) {
            Facture facture = factureRepository.findById(paiement.getFacture().getId())
                    .orElseThrow(() -> new RuntimeException("Facture introuvable"));

            existingPaiement.setFacture(facture);
        }

        if (paiement.getStudent() != null && paiement.getStudent().getId() != null) {
            Student student = studentRepository.findById(paiement.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

            existingPaiement.setStudent(student);
        }

        existingPaiement.setMontant(paiement.getMontant());
        existingPaiement.setDatePaiement(paiement.getDatePaiement());
        existingPaiement.setModePaiement(paiement.getModePaiement());

        Paiement updatedPaiement = paiementRepository.save(existingPaiement);

        updateFactureStatus(oldFactureId);
        updateFactureStatus(updatedPaiement.getFacture().getId());

        return updatedPaiement;
    }

    public void deletePaiement(Long id) {
        Paiement paiement = getPaiementById(id);
        Long factureId = paiement.getFacture().getId();

        paiementRepository.delete(paiement);

        updateFactureStatus(factureId);
    }

    private void updateFactureStatus(Long factureId) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        BigDecimal totalPaid = paiementRepository.getTotalPaidByFactureId(factureId);
        BigDecimal montantFacture = facture.getMontant();

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            facture.setStatutPaiement("NON_PAYEE");
        } else if (totalPaid.compareTo(montantFacture) >= 0) {
            facture.setStatutPaiement("PAYEE");
        } else {
            facture.setStatutPaiement("PARTIELLE");
        }

        factureRepository.save(facture);
    }
}