package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Student;
import org.example.gestionacademic.exception.ResourceNotFoundException;
import org.example.gestionacademic.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.List;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Étudiant introuvable avec l'id : " + id
                        )
                );
    }

    @Transactional(readOnly = true)
    public List<Student> searchStudents(String keyword) {
        return studentRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
                        keyword, keyword
                );
    }

    public Student createStudent(Student student) {
        verifyUniqueFields(student, null);
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student newStudent) {
        Student student = getStudentById(id);

        verifyUniqueFields(newStudent, id);

        student.setLastName(newStudent.getLastName());
        student.setFirstName(newStudent.getFirstName());
        student.setCin(newStudent.getCin());
        student.setStudentIdentifier(newStudent.getStudentIdentifier());
        student.setEmail(newStudent.getEmail());
        student.setPhone(newStudent.getPhone());
        student.setFiliere(newStudent.getFiliere());
        student.setStudyLevel(newStudent.getStudyLevel());
        student.setPhoto(newStudent.getPhoto());
        student.setAddress(newStudent.getAddress());

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }

    private void verifyUniqueFields(Student student, Long currentId) {
        studentRepository.findByCin(student.getCin())
                .filter(existing -> !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Le CIN existe déjà"
                    );
                });

        studentRepository
                .findByStudentIdentifier(student.getStudentIdentifier())
                .filter(existing -> !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "L'identifiant étudiant existe déjà"
                    );
                });

        studentRepository.findByEmail(student.getEmail())
                .filter(existing -> !existing.getId().equals(currentId))
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "L'adresse email existe déjà"
                    );
                });
    }
    public Student uploadStudentPhoto(Long id, MultipartFile file) {
        Student student = getStudentById(id);

        try {
            String uploadDir = "uploads/students/";

            Files.createDirectories(Paths.get(uploadDir));

            String originalFilename = file.getOriginalFilename();
            String extension = "";

            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String fileName = UUID.randomUUID() + extension;

            Path filePath = Paths.get(uploadDir + fileName);
            Files.write(filePath, file.getBytes());

            student.setPhoto(fileName);

            return studentRepository.save(student);

        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'upload de la photo");
        }
    }
}