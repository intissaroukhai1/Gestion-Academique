package org.example.gestionacademic.controller;

import jakarta.validation.Valid;
import org.example.gestionacademic.entity.Absence;
import org.example.gestionacademic.service.AbsenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "http://localhost:4200")
public class AbsenceController {

    private final AbsenceService absenceService;

    public AbsenceController(AbsenceService absenceService) {
        this.absenceService = absenceService;
    }

    @GetMapping
    public List<Absence> getAllAbsences() {
        return absenceService.getAllAbsences();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Absence> getAbsenceById(@PathVariable Long id) {
        return ResponseEntity.ok(absenceService.getAbsenceById(id));
    }

    @PostMapping
    public ResponseEntity<Absence> createAbsence(@Valid @RequestBody Absence absence) {
        return ResponseEntity.ok(absenceService.createAbsence(absence));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Absence> updateAbsence(
            @PathVariable Long id,
            @Valid @RequestBody Absence absence
    ) {
        return ResponseEntity.ok(absenceService.updateAbsence(id, absence));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.noContent().build();
    }
}