package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.DocumentStage;
import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.repository.DocumentStageRepository;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentStageService {

    private final DocumentStageRepository documentStageRepository;
    private final StudentRepository studentRepository;

    public DocumentStageService(
            DocumentStageRepository documentStageRepository,
            StudentRepository studentRepository
    ) {
        this.documentStageRepository = documentStageRepository;
        this.studentRepository = studentRepository;
    }

    public List<DocumentStage> getAllDocuments() {
        return documentStageRepository.findAll();
    }

    public DocumentStage getDocumentById(Long id) {
        return documentStageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document de stage introuvable avec id : " + id));
    }

    public List<DocumentStage> getDocumentsByStudent(Long studentId) {
        return documentStageRepository.findByStudentId(studentId);
    }

    public List<DocumentStage> getDocumentsByStatut(String statut) {
        return documentStageRepository.findByStatut(statut);
    }

    public List<DocumentStage> getDocumentsByType(String typeDocument) {
        return documentStageRepository.findByTypeDocument(typeDocument);
    }

    public DocumentStage createDocument(DocumentStage documentStage) {
        if (documentStage.getStudent() == null || documentStage.getStudent().getId() == null) {
            throw new RuntimeException("L'étudiant est obligatoire");
        }

        Student student = studentRepository.findById(documentStage.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        documentStage.setStudent(student);

        if (documentStage.getDateDepot() == null) {
            documentStage.setDateDepot(LocalDate.now());
        }

        if (documentStage.getStatut() == null || documentStage.getStatut().isBlank()) {
            documentStage.setStatut("EN_ATTENTE");
        }

        return documentStageRepository.save(documentStage);
    }

    public DocumentStage updateDocument(Long id, DocumentStage documentStage) {
        DocumentStage existingDocument = getDocumentById(id);

        existingDocument.setTitre(documentStage.getTitre());
        existingDocument.setTypeDocument(documentStage.getTypeDocument());
        existingDocument.setNomFichier(documentStage.getNomFichier());
        existingDocument.setCheminFichier(documentStage.getCheminFichier());
        existingDocument.setDateDepot(documentStage.getDateDepot());
        existingDocument.setStatut(documentStage.getStatut());
        existingDocument.setCommentaire(documentStage.getCommentaire());

        if (documentStage.getStudent() != null && documentStage.getStudent().getId() != null) {
            Student student = studentRepository.findById(documentStage.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

            existingDocument.setStudent(student);
        }

        return documentStageRepository.save(existingDocument);
    }

    public DocumentStage validerDocument(Long id, String commentaire) {
        DocumentStage document = getDocumentById(id);
        document.setStatut("VALIDE");
        document.setCommentaire(commentaire);
        return documentStageRepository.save(document);
    }

    public DocumentStage refuserDocument(Long id, String commentaire) {
        DocumentStage document = getDocumentById(id);
        document.setStatut("REFUSE");
        document.setCommentaire(commentaire);
        return documentStageRepository.save(document);
    }

    public void deleteDocument(Long id) {
        DocumentStage document = getDocumentById(id);
        documentStageRepository.delete(document);
    }
    public DocumentStage uploadDocument(
            String titre,
            String typeDocument,
            String statut,
            String commentaire,
            Long studentId,
            MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("Le fichier est obligatoire");
        }

        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            throw new RuntimeException("Seuls les fichiers PDF sont acceptés");
        }

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        try {
            String uploadDir = "uploads/stages";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String fileName = System.currentTimeMillis() + "_" + originalFileName;

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            DocumentStage documentStage = new DocumentStage();
            documentStage.setTitre(titre);
            documentStage.setTypeDocument(typeDocument);
            documentStage.setStatut(statut == null || statut.isBlank() ? "EN_ATTENTE" : statut);
            documentStage.setCommentaire(commentaire);
            documentStage.setDateDepot(LocalDate.now());
            documentStage.setNomFichier(fileName);
            documentStage.setCheminFichier(filePath.toString());
            documentStage.setStudent(student);

            return documentStageRepository.save(documentStage);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload du fichier : " + e.getMessage());
        }
    }
}