package org.example.gestionacademic.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    @Column(nullable = false, length = 80)
    private String lastName;

    @NotBlank(message = "Le prénom est obligatoire")
    @Column(nullable = false, length = 80)
    private String firstName;

    @NotBlank(message = "Le CIN est obligatoire")
    @Column(nullable = false, unique = true, length = 20)
    private String cin;

    @NotBlank(message = "L'identifiant étudiant est obligatoire")
    @Column(nullable = false, unique = true, length = 30)
    private String studentIdentifier;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @NotBlank(message = "La filière est obligatoire")
    @Column(nullable = false, length = 100)
    private String program;

    @NotBlank(message = "Le niveau d'étude est obligatoire")
    @Column(nullable = false, length = 50)
    private String studyLevel;

    @Column(length = 255)
    private String photo;

    @Column(length = 255)
    private String address;

    public Student() {
    }

    public Student(String lastName, String firstName, String cin,
                   String studentIdentifier, String email, String phone,
                   String program, String studyLevel, String photo,
                   String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.cin = cin;
        this.studentIdentifier = studentIdentifier;
        this.email = email;
        this.phone = phone;
        this.program = program;
        this.studyLevel = studyLevel;
        this.photo = photo;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getStudentIdentifier() {
        return studentIdentifier;
    }

    public void setStudentIdentifier(String studentIdentifier) {
        this.studentIdentifier = studentIdentifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getStudyLevel() {
        return studyLevel;
    }

    public void setStudyLevel(String studyLevel) {
        this.studyLevel = studyLevel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}