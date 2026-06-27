package org.example.gestionacademic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "matieres")
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la matière est obligatoire")
    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 255)
    private String description;

    @NotNull(message = "Le coefficient est obligatoire")
    @Column(nullable = false)
    private Double coefficient;

    @Column(length = 50)
    private String semestre;

    @NotNull(message = "La filière est obligatoire")
    @ManyToOne
    @JoinColumn(name = "filiere_id", nullable = false)
    private Filiere filiere;

    public Matiere() {
    }

    public Matiere(String nom, String description, Double coefficient, String semestre, Filiere filiere) {
        this.nom = nom;
        this.description = description;
        this.coefficient = coefficient;
        this.semestre = semestre;
        this.filiere = filiere;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }
}