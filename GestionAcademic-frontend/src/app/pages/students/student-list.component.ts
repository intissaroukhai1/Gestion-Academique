import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SelectModule } from 'primeng/select';

import { Student } from './student.model';
import { StudentService } from './student.service';
import { Filiere } from '../filieres/filiere.model';
import { FiliereService } from '../filieres/filiere.service';

@Component({
    selector: 'app-student-list',
    standalone: true,
    imports: [CommonModule, FormsModule, SelectModule],
    templateUrl: './student-list.component.html',
    styleUrl: './student-list.component.scss'
})
export class StudentListComponent implements OnInit {
    private studentService = inject(StudentService);
    private filiereService = inject(FiliereService);
    private cdr = inject(ChangeDetectorRef);

    students: Student[] = [];
    filieres: Filiere[] = [];

    student: Student = this.emptyStudent();

    selectedFile: File | null = null;
    selectedStudent: Student | null = null;

    errorMessage = '';
    successMessage = '';
    loading = false;

    ngOnInit(): void {
        this.loadStudents();
        this.loadFilieres();
    }

    loadStudents(): void {
        this.loading = true;
        this.errorMessage = '';

        this.studentService.getAll().subscribe({
            next: (data) => {
                this.students = [...data];
                this.loading = false;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement étudiants : ', error);
                this.errorMessage = 'Impossible de charger les étudiants.';
                this.loading = false;
                this.cdr.detectChanges();
            }
        });
    }

    loadFilieres(): void {
        this.filiereService.getFilieres().subscribe({
            next: (data) => {
                this.filieres = data;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement filières : ', error);
            }
        });
    }

    onFileSelected(event: Event): void {
        const input = event.target as HTMLInputElement;

        if (input.files && input.files.length > 0) {
            this.selectedFile = input.files[0];
        }
    }

    save(): void {
        this.errorMessage = '';
        this.successMessage = '';

        if (
            !this.student.lastName.trim() ||
            !this.student.firstName.trim() ||
            !this.student.cin.trim() ||
            !this.student.studentIdentifier.trim() ||
            !this.student.email.trim() ||
            !this.student.filiere ||
            !this.student.studyLevel.trim()
        ) {
            this.errorMessage = 'Veuillez remplir tous les champs obligatoires.';
            return;
        }

        const isEditMode = !!this.student.id;

        const request = isEditMode
            ? this.studentService.update(this.student.id!, this.student)
            : this.studentService.create(this.student);

        request.subscribe({
            next: (savedStudent) => {
                if (this.selectedFile && savedStudent.id) {
                    this.uploadPhotoAfterSave(savedStudent.id, isEditMode);
                } else {
                    this.finishSave(isEditMode);
                }
            },
            error: (error) => {
                console.error('Erreur ajout/modification étudiant : ', error);
                this.errorMessage =
                    'Erreur pendant l’enregistrement. Vérifiez le CIN, l’identifiant ou l’email.';
                this.cdr.detectChanges();
            }
        });
    }

    private uploadPhotoAfterSave(studentId: number, isEditMode: boolean): void {
        if (!this.selectedFile) {
            this.finishSave(isEditMode);
            return;
        }

        this.studentService.uploadPhoto(studentId, this.selectedFile).subscribe({
            next: () => {
                this.selectedFile = null;
                this.finishSave(isEditMode);
            },
            error: (error) => {
                console.error('Erreur upload photo : ', error);
                this.errorMessage =
                    'Étudiant enregistré, mais erreur lors de l’upload de la photo.';
                this.loadStudents();
                this.cdr.detectChanges();
            }
        });
    }

    private finishSave(isEditMode: boolean): void {
        this.successMessage = isEditMode
            ? 'Étudiant modifié avec succès.'
            : 'Étudiant ajouté avec succès.';

        this.student = this.emptyStudent();
        this.selectedFile = null;
        this.loadStudents();
        this.cdr.detectChanges();
    }

    showDetails(student: Student): void {
        this.selectedStudent = student;
        this.errorMessage = '';
        this.successMessage = '';
        this.cdr.detectChanges();
    }

    closeDetails(): void {
        this.selectedStudent = null;
        this.cdr.detectChanges();
    }

    edit(student: Student): void {
        this.student = { ...student };
        this.selectedFile = null;
        this.successMessage = '';
        this.errorMessage = '';

        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    editFromDetails(): void {
        if (!this.selectedStudent) {
            return;
        }

        this.edit(this.selectedStudent);
        this.closeDetails();
    }

    remove(id?: number): void {
        if (!id) {
            return;
        }

        if (confirm('Voulez-vous vraiment supprimer cet étudiant ?')) {
            this.studentService.delete(id).subscribe({
                next: () => {
                    this.successMessage = 'Étudiant supprimé avec succès.';
                    this.errorMessage = '';
                    this.selectedStudent = null;
                    this.loadStudents();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur suppression étudiant : ', error);
                    this.errorMessage = 'Erreur pendant la suppression.';
                    this.cdr.detectChanges();
                }
            });
        }
    }

    cancel(): void {
        this.student = this.emptyStudent();
        this.selectedFile = null;
        this.errorMessage = '';
        this.successMessage = '';
    }

    getStudentPhotoUrl(photo?: string): string {
        if (!photo) {
            return '';
        }

        return 'http://localhost:8080/uploads/students/' + photo;
    }

    private emptyStudent(): Student {
        return {
            lastName: '',
            firstName: '',
            cin: '',
            studentIdentifier: '',
            email: '',
            phone: '',
            program: '',
            studyLevel: '',
            photo: '',
            address: '',
            filiere: null
        };
    }
}