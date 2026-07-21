package org.example.gestionacademic.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMenu;

    @Column(nullable = false, unique = true)
    private Long codeMenu;

    @Column(nullable = false)
    private String libelle;

    private String route;

    private String icon;

    private Integer ordre;

    private Integer level;

    private Long parentId;

    private String actif = "OUI";

    private LocalDateTime dateCreation;

    private String createur;

    private LocalDateTime dateModification;

    private String modificateur;

    public Menu() {
    }

    @PrePersist
    public void prePersist() {
        this.dateCreation = LocalDateTime.now();

        if (this.actif == null) {
            this.actif = "OUI";
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }

    public Long getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Long idMenu) {
        this.idMenu = idMenu;
    }

    public Long getCodeMenu() {
        return codeMenu;
    }

    public void setCodeMenu(Long codeMenu) {
        this.codeMenu = codeMenu;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getActif() {
        return actif;
    }

    public void setActif(String actif) {
        this.actif = actif;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }

    public String getModificateur() {
        return modificateur;
    }

    public void setModificateur(String modificateur) {
        this.modificateur = modificateur;
    }
}