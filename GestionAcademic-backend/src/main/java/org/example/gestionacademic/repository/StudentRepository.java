package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByCin(String cin);

    Optional<Student> findByStudentIdentifier(String studentIdentifier);

    Optional<Student> findByEmail(String email);

    boolean existsByCin(String cin);

    boolean existsByStudentIdentifier(String studentIdentifier);

    boolean existsByEmail(String email);

    List<Student>
    findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(
            String firstName,
            String lastName
    );

    List<Student> findByProgramIgnoreCase(String program);

    List<Student> findByStudyLevelIgnoreCase(String studyLevel);
}