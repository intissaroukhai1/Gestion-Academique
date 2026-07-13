package org.example.gestionacademic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "documents_stage")
public class DocumentStage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le titre du document est obligatoire")
    @Column(nullable = false)
    private String titre;

    @NotBlank(message = "Le type du document est obligatoire")
    @Column(name = "type_document", nullable = false)
    private String typeDocument;

    @Column(name = "nom_fichier")
    private String nomFichier;

    @Column(name = "chemin_fichier")
    private String cheminFichier;

    @Column(name = "date_depot", nullable = false)
    private LocalDate dateDepot;

    @Column(nullable = false)
    private String statut = "EN_ATTENTE";

    @Column(length = 1000)
    private String commentaire;

    @NotNull(message = "L'étudiant est obligatoire")
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public DocumentStage() {
    }

    @PrePersist
    public void prePersist() {
        if (this.dateDepot == null) {
            this.dateDepot = LocalDate.now();
        }

        if (this.statut == null || this.statut.isBlank()) {
            this.statut = "EN_ATTENTE";
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTypeDocument() {
        return typeDocument;
    }

    public void setTypeDocument(String typeDocument) {
        this.typeDocument = typeDocument;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getCheminFichier() {
        return cheminFichier;
    }

    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }

    public LocalDate getDateDepot() {
        return dateDepot;
    }

    public void setDateDepot(LocalDate dateDepot) {
        this.dateDepot = dateDepot;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}