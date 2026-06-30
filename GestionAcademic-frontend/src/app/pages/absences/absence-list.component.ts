import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { FluidModule } from 'primeng/fluid';
import { SelectModule } from 'primeng/select';
import { CheckboxModule } from 'primeng/checkbox';
import { TagModule } from 'primeng/tag';

import { Absence } from './absence.model';
import { AbsenceService } from './absence.service';

import { Student } from '../students/student.model';
import { StudentService } from '../students/student.service';

import { Matiere } from '../matieres/matiere.model';
import { MatiereService } from '../matieres/matiere.service';

@Component({
    selector: 'app-absence-list',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        TableModule,
        ButtonModule,
        InputTextModule,
        TextareaModule,
        FluidModule,
        SelectModule,
        CheckboxModule,
        TagModule
    ],
    templateUrl: './absence-list.component.html',
    styleUrls: ['./absence-list.component.scss']
})
export class AbsenceListComponent implements OnInit {
    private absenceService = inject(AbsenceService);
    private studentService = inject(StudentService);
    private matiereService = inject(MatiereService);
    private cdr = inject(ChangeDetectorRef);

    absences: Absence[] = [];
    students: Student[] = [];
    matieres: Matiere[] = [];

    absence: Absence = this.emptyAbsence();

    editMode = false;
    selectedId?: number;

    ngOnInit(): void {
        this.loadAbsences();
        this.loadStudents();
        this.loadMatieres();
    }

    loadAbsences(): void {
        this.absenceService.getAbsences().subscribe({
            next: (data) => {
                this.absences = [...data];
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement absences : ', error);
            }
        });
    }

    loadStudents(): void {
        this.studentService.getAll().subscribe({
            next: (data) => {
                this.students = data;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement étudiants : ', error);
            }
        });
    }

    loadMatieres(): void {
        this.matiereService.getMatieres().subscribe({
            next: (data) => {
                this.matieres = data;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement matières : ', error);
            }
        });
    }

    saveAbsence(): void {
        if (
            !this.absence.dateAbsence ||
            !this.absence.student ||
            !this.absence.matiere
        ) {
            return;
        }

        if (this.editMode && this.selectedId) {
            this.absenceService.updateAbsence(this.selectedId, this.absence).subscribe({
                next: (updatedAbsence) => {
                    this.absences = this.absences.map((a) =>
                        a.id === this.selectedId ? updatedAbsence : a
                    );
                    this.resetForm();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur modification absence : ', error);
                }
            });
        } else {
            this.absenceService.addAbsence(this.absence).subscribe({
                next: (newAbsence) => {
                    this.absences = [...this.absences, newAbsence];
                    this.resetForm();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur ajout absence : ', error);
                }
            });
        }
    }

    editAbsence(absence: Absence): void {
        this.editMode = true;
        this.selectedId = absence.id;
        this.absence = { ...absence };

        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    deleteAbsence(id: number): void {
        if (confirm('Voulez-vous supprimer cette absence ?')) {
            this.absenceService.deleteAbsence(id).subscribe({
                next: () => {
                    this.absences = this.absences.filter((a) => a.id !== id);
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur suppression absence : ', error);
                }
            });
        }
    }

    resetForm(): void {
        this.absence = this.emptyAbsence();
        this.editMode = false;
        this.selectedId = undefined;
    }

    refreshAbsences(): void {
        this.resetForm();
        this.loadAbsences();
    }

    private emptyAbsence(): Absence {
        return {
            dateAbsence: '',
            justifiee: false,
            motif: '',
            student: null,
            matiere: null
        };
    }
}