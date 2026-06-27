package org.example.gestionacademic.controller;

import jakarta.validation.Valid;
import org.example.gestionacademic.entity.Matiere;
import org.example.gestionacademic.service.MatiereService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matieres")
@CrossOrigin(origins = "http://localhost:4200")
public class MatiereController {

    private final MatiereService matiereService;

    public MatiereController(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    @GetMapping
    public List<Matiere> getAllMatieres() {
        return matiereService.getAllMatieres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable Long id) {
        return ResponseEntity.ok(matiereService.getMatiereById(id));
    }

    @PostMapping
    public ResponseEntity<Matiere> createMatiere(@Valid @RequestBody Matiere matiere) {
        return ResponseEntity.ok(matiereService.createMatiere(matiere));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Matiere> updateMatiere(
            @PathVariable Long id,
            @Valid @RequestBody Matiere matiere
    ) {
        return ResponseEntity.ok(matiereService.updateMatiere(id, matiere));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        matiereService.deleteMatiere(id);
        return ResponseEntity.noContent().build();
    }
}