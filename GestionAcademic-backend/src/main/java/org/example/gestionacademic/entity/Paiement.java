package org.example.gestionacademic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "paiements")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_paiement", nullable = false)
    private LocalDate datePaiement;

    @Column(name = "mode_paiement", nullable = false, length = 30)
    private String modePaiement;

    @NotNull(message = "La facture est obligatoire")
    @ManyToOne
    @JoinColumn(name = "facture_id", nullable = false)
    private Facture facture;

    @NotNull(message = "L'étudiant est obligatoire")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Paiement() {
    }

    @PrePersist
    public void prePersist() {
        if (this.datePaiement == null) {
            this.datePaiement = LocalDate.now();
        }

        if (this.modePaiement == null || this.modePaiement.isBlank()) {
            this.modePaiement = "ESPECE";
        }
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}