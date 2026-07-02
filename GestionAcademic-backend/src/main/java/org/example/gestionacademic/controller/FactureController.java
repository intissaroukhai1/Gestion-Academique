package org.example.gestionacademic.controller;

import jakarta.validation.Valid;
import org.example.gestionacademic.entity.Facture;
import org.example.gestionacademic.service.FactureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factures")
@CrossOrigin(origins = "http://localhost:4200")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping
    public List<Facture> getAllFactures() {
        return factureService.getAllFactures();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return ResponseEntity.ok(factureService.getFactureById(id));
    }

    @GetMapping("/student/{studentId}")
    public List<Facture> getFacturesByStudent(@PathVariable Long studentId) {
        return factureService.getFacturesByStudent(studentId);
    }

    @GetMapping("/statut/{statutPaiement}")
    public List<Facture> getFacturesByStatut(@PathVariable String statutPaiement) {
        return factureService.getFacturesByStatut(statutPaiement);
    }

    @PostMapping
    public ResponseEntity<Facture> createFacture(@Valid @RequestBody Facture facture) {
        return ResponseEntity.ok(factureService.createFacture(facture));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Facture> updateFacture(
            @PathVariable Long id,
            @Valid @RequestBody Facture facture
    ) {
        return ResponseEntity.ok(factureService.updateFacture(id, facture));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        factureService.deleteFacture(id);
        return ResponseEntity.noContent().build();
    }
}