package org.example.gestionacademic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "factures")
public class Facture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_facture", nullable = false, unique = true, length = 50)
    private String numeroFacture;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_emission", nullable = false)
    private LocalDate dateEmission;

    @Column(name = "statut_paiement", nullable = false, length = 30)
    private String statutPaiement = "NON_PAYEE";

    @Column(name = "mode_paiement", length = 30)
    private String modePaiement;

    @NotNull(message = "L'étudiant est obligatoire")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Facture() {
    }

    @PrePersist
    public void prePersist() {
        if (this.dateEmission == null) {
            this.dateEmission = LocalDate.now();
        }

        if (this.statutPaiement == null || this.statutPaiement.isBlank()) {
            this.statutPaiement = "NON_PAYEE";
        }
    }

    public Long getId() {
        return id;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}