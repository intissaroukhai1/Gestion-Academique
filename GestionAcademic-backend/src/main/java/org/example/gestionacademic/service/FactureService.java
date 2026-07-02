package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Facture;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.FactureRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FactureService {

    private final FactureRepository factureRepository;
    private final StudentRepository studentRepository;

    public FactureService(
            FactureRepository factureRepository,
            StudentRepository studentRepository
    ) {
        this.factureRepository = factureRepository;
        this.studentRepository = studentRepository;
    }

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }

    public Facture getFactureById(Long id) {
        return factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable avec id : " + id));
    }

    public List<Facture> getFacturesByStudent(Long studentId) {
        return factureRepository.findByStudentId(studentId);
    }

    public List<Facture> getFacturesByStatut(String statutPaiement) {
        return factureRepository.findByStatutPaiement(statutPaiement);
    }

    public Facture createFacture(Facture facture) {
        if (facture.getStudent() == null || facture.getStudent().getId() == null) {
            throw new RuntimeException("L'étudiant est obligatoire pour créer une facture");
        }

        Student student = studentRepository.findById(facture.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        facture.setStudent(student);

        if (facture.getNumeroFacture() == null || facture.getNumeroFacture().isBlank()) {
            facture.setNumeroFacture(generateNumeroFacture());
        }

        if (facture.getDateEmission() == null) {
            facture.setDateEmission(LocalDate.now());
        }

        if (facture.getStatutPaiement() == null || facture.getStatutPaiement().isBlank()) {
            facture.setStatutPaiement("NON_PAYEE");
        }

        return factureRepository.save(facture);
    }

    public Facture updateFacture(Long id, Facture facture) {
        Facture existingFacture = getFactureById(id);

        if (facture.getStudent() != null && facture.getStudent().getId() != null) {
            Student student = studentRepository.findById(facture.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

            existingFacture.setStudent(student);
        }

        existingFacture.setMontant(facture.getMontant());
        existingFacture.setDateEmission(facture.getDateEmission());
        existingFacture.setStatutPaiement(facture.getStatutPaiement());
        existingFacture.setModePaiement(facture.getModePaiement());

        if (facture.getNumeroFacture() != null && !facture.getNumeroFacture().isBlank()) {
            existingFacture.setNumeroFacture(facture.getNumeroFacture());
        }

        return factureRepository.save(existingFacture);
    }

    public void deleteFacture(Long id) {
        Facture facture = getFactureById(id);
        factureRepository.delete(facture);
    }

    private String generateNumeroFacture() {
        long count = factureRepository.count() + 1;
        int year = LocalDate.now().getYear();

        return "FAC-" + year + "-" + String.format("%04d", count);
    }
}