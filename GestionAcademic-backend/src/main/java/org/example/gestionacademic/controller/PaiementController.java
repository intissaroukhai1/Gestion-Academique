package org.example.gestionacademic.controller;

import jakarta.validation.Valid;
import org.example.gestionacademic.entity.Paiement;
import org.example.gestionacademic.service.PaiementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "http://localhost:4200")
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping
    public List<Paiement> getAllPaiements() {
        return paiementService.getAllPaiements();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Long id) {
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    @GetMapping("/student/{studentId}")
    public List<Paiement> getPaiementsByStudent(@PathVariable Long studentId) {
        return paiementService.getPaiementsByStudent(studentId);
    }

    @GetMapping("/facture/{factureId}")
    public List<Paiement> getPaiementsByFacture(@PathVariable Long factureId) {
        return paiementService.getPaiementsByFacture(factureId);
    }

    @PostMapping
    public ResponseEntity<Paiement> createPaiement(@Valid @RequestBody Paiement paiement) {
        return ResponseEntity.ok(paiementService.createPaiement(paiement));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paiement> updatePaiement(
            @PathVariable Long id,
            @Valid @RequestBody Paiement paiement
    ) {
        return ResponseEntity.ok(paiementService.updatePaiement(id, paiement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}