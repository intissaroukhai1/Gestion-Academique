package org.example.gestionacademic.controller;

import jakarta.validation.Valid;
import org.example.gestionacademic.entity.DocumentStage;
import org.example.gestionacademic.service.DocumentStageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/documents-stage")
@CrossOrigin(origins = "http://localhost:4200")
public class DocumentStageController {

    private final DocumentStageService documentStageService;

    public DocumentStageController(DocumentStageService documentStageService) {
        this.documentStageService = documentStageService;
    }

    @GetMapping
    public List<DocumentStage> getAllDocuments() {
        return documentStageService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentStage> getDocumentById(@PathVariable Long id) {
        return ResponseEntity.ok(documentStageService.getDocumentById(id));
    }

    @GetMapping("/student/{studentId}")
    public List<DocumentStage> getDocumentsByStudent(@PathVariable Long studentId) {
        return documentStageService.getDocumentsByStudent(studentId);
    }

    @GetMapping("/statut/{statut}")
    public List<DocumentStage> getDocumentsByStatut(@PathVariable String statut) {
        return documentStageService.getDocumentsByStatut(statut);
    }

    @GetMapping("/type/{typeDocument}")
    public List<DocumentStage> getDocumentsByType(@PathVariable String typeDocument) {
        return documentStageService.getDocumentsByType(typeDocument);
    }

    @PostMapping
    public ResponseEntity<DocumentStage> createDocument(@Valid @RequestBody DocumentStage documentStage) {
        return ResponseEntity.ok(documentStageService.createDocument(documentStage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentStage> updateDocument(
            @PathVariable Long id,
            @Valid @RequestBody DocumentStage documentStage
    ) {
        return ResponseEntity.ok(documentStageService.updateDocument(id, documentStage));
    }

    @PutMapping("/{id}/valider")
    public ResponseEntity<DocumentStage> validerDocument(
            @PathVariable Long id,
            @RequestParam(required = false) String commentaire
    ) {
        return ResponseEntity.ok(documentStageService.validerDocument(id, commentaire));
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<DocumentStage> refuserDocument(
            @PathVariable Long id,
            @RequestParam(required = false) String commentaire
    ) {
        return ResponseEntity.ok(documentStageService.refuserDocument(id, commentaire));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentStageService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/upload")
    public ResponseEntity<DocumentStage> uploadDocument(
            @RequestParam("titre") String titre,
            @RequestParam("typeDocument") String typeDocument,
            @RequestParam(value = "statut", required = false) String statut,
            @RequestParam(value = "commentaire", required = false) String commentaire,
            @RequestParam("studentId") Long studentId,
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(
                documentStageService.uploadDocument(
                        titre,
                        typeDocument,
                        statut,
                        commentaire,
                        studentId,
                        file
                )
        );
    }
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> downloadDocument(@PathVariable Long id) {
        try {
            DocumentStage document = documentStageService.getDocumentById(id);

            Path filePath = Paths.get(document.getCheminFichier());
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("Fichier introuvable");
            }

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + document.getNomFichier() + "\""
                    )
                    .body(resource);

        } catch (Exception e) {
            throw new RuntimeException("Erreur téléchargement fichier : " + e.getMessage());
        }
    }
}