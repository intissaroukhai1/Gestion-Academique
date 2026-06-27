package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Filiere;
import org.example.gestionacademic.exception.ResourceNotFoundException;
import org.example.gestionacademic.repository.FiliereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FiliereService {

    private final FiliereRepository filiereRepository;

    public FiliereService(FiliereRepository filiereRepository) {
        this.filiereRepository = filiereRepository;
    }

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Filière introuvable avec l'id : " + id)
                );
    }

    public Filiere createFiliere(Filiere filiere) {
        if (filiereRepository.existsByNomIgnoreCase(filiere.getNom())) {
            throw new IllegalArgumentException("Cette filière existe déjà");
        }

        return filiereRepository.save(filiere);
    }

    public Filiere updateFiliere(Long id, Filiere newFiliere) {
        Filiere filiere = getFiliereById(id);

        filiereRepository.findByNomIgnoreCase(newFiliere.getNom())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Cette filière existe déjà");
                });

        filiere.setNom(newFiliere.getNom());
        filiere.setDescription(newFiliere.getDescription());

        return filiereRepository.save(filiere);
    }

    public void deleteFiliere(Long id) {
        Filiere filiere = getFiliereById(id);
        filiereRepository.delete(filiere);
    }
}