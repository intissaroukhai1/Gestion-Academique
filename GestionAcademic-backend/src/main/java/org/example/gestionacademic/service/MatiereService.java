package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Filiere;
import org.example.gestionacademic.entity.Matiere;
import org.example.gestionacademic.repository.FiliereRepository;
import org.example.gestionacademic.repository.MatiereRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {

    private final MatiereRepository matiereRepository;
    private final FiliereRepository filiereRepository;

    public MatiereService(
            MatiereRepository matiereRepository,
            FiliereRepository filiereRepository
    ) {
        this.matiereRepository = matiereRepository;
        this.filiereRepository = filiereRepository;
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    public Matiere getMatiereById(Long id) {
        return matiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matière introuvable avec id : " + id));
    }

    public Matiere createMatiere(Matiere matiere) {
        Long filiereId = matiere.getFiliere().getId();

        Filiere filiere = filiereRepository.findById(filiereId)
                .orElseThrow(() -> new RuntimeException("Filière introuvable avec id : " + filiereId));

        matiere.setFiliere(filiere);

        return matiereRepository.save(matiere);
    }

    public Matiere updateMatiere(Long id, Matiere matiere) {
        Matiere existingMatiere = getMatiereById(id);

        existingMatiere.setNom(matiere.getNom());
        existingMatiere.setDescription(matiere.getDescription());
        existingMatiere.setCoefficient(matiere.getCoefficient());
        existingMatiere.setSemestre(matiere.getSemestre());

        if (matiere.getFiliere() != null && matiere.getFiliere().getId() != null) {
            Filiere filiere = filiereRepository.findById(matiere.getFiliere().getId())
                    .orElseThrow(() -> new RuntimeException("Filière introuvable avec id : " + matiere.getFiliere().getId()));

            existingMatiere.setFiliere(filiere);
        }

        return matiereRepository.save(existingMatiere);
    }

    public void deleteMatiere(Long id) {
        Matiere matiere = getMatiereById(id);
        matiereRepository.delete(matiere);
    }
}