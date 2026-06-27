import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FluidModule } from 'primeng/fluid';
import { SelectModule } from 'primeng/select';

import { Note } from './note.model';
import { NoteService } from './note.service';

import { Student } from '../students/student.model';
import { StudentService } from '../students/student.service';

import { Matiere } from '../matieres/matiere.model';
import { MatiereService } from '../matieres/matiere.service';

@Component({
    selector: 'app-note-list',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        TableModule,
        ButtonModule,
        InputTextModule,
        FluidModule,
        SelectModule
    ],
    templateUrl: './note-list.component.html',
    styleUrls: ['./note-list.component.scss']
})
export class NoteListComponent implements OnInit {
    private noteService = inject(NoteService);
    private studentService = inject(StudentService);
    private matiereService = inject(MatiereService);
    private cdr = inject(ChangeDetectorRef);

    notes: Note[] = [];
    filteredNotes: Note[] = [];

selectedStudentFilter: Student | null = null;
selectedMatiereFilter: Matiere | null = null;
selectedTypeFilter: string | null = null;
    students: Student[] = [];
    matieres: Matiere[] = [];

    note: Note = this.emptyNote();

    editMode = false;
    selectedId?: number;

    typeEvaluations = [
        { label: 'DS', value: 'DS' },
        { label: 'Examen', value: 'Examen' },
        { label: 'TP', value: 'TP' },
        { label: 'Projet', value: 'Projet' }
    ];

    ngOnInit(): void {
        this.loadNotes();
        this.loadStudents();
        this.loadMatieres();
    }

    loadNotes(): void {
        this.noteService.getNotes().subscribe({
            next: (data) => {
                this.notes = [...data];
                this.filteredNotes = [...data];
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement notes : ', error);
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

    saveNote(): void {
        if (
            this.note.valeur === null ||
            this.note.valeur < 0 ||
            this.note.valeur > 20 ||
            !this.note.typeEvaluation ||
            !this.note.dateEvaluation ||
            !this.note.student ||
            !this.note.matiere
        ) {
            return;
        }

        if (this.editMode && this.selectedId) {
            this.noteService.updateNote(this.selectedId, this.note).subscribe({
                next: (updatedNote) => {
                    this.notes = this.notes.map((n) =>
                        n.id === this.selectedId ? updatedNote : n
                    );
                    this.resetForm();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur modification note : ', error);
                }
            });
        } else {
            this.noteService.addNote(this.note).subscribe({
                next: (newNote) => {
                    this.notes = [...this.notes, newNote];
                    this.resetForm();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur ajout note : ', error);
                }
            });
        }
    }

    editNote(note: Note): void {
        this.editMode = true;
        this.selectedId = note.id;
        this.note = { ...note };

        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    deleteNote(id: number): void {
        if (confirm('Voulez-vous supprimer cette note ?')) {
            this.noteService.deleteNote(id).subscribe({
                next: () => {
                    this.notes = this.notes.filter((n) => n.id !== id);
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur suppression note : ', error);
                }
            });
        }
    }

    resetForm(): void {
        this.note = this.emptyNote();
        this.editMode = false;
        this.selectedId = undefined;
    }

    refreshNotes(): void {
        this.resetForm();
        this.loadNotes();
    }

    getStudentFullName(student: Student | null): string {
        if (!student) {
            return '';
        }

        return `${student.firstName} ${student.lastName}`;
    }

    private emptyNote(): Note {
        return {
            valeur: 0,
            typeEvaluation: '',
            dateEvaluation: '',
            student: null,
            matiere: null
        };
    }
    applyFilters(): void {
    this.filteredNotes = this.notes.filter((note) => {
        const matchStudent =
            !this.selectedStudentFilter ||
            note.student?.id === this.selectedStudentFilter.id;

        const matchMatiere =
            !this.selectedMatiereFilter ||
            note.matiere?.id === this.selectedMatiereFilter.id;

        const matchType =
            !this.selectedTypeFilter ||
            note.typeEvaluation === this.selectedTypeFilter;

        return matchStudent && matchMatiere && matchType;
    });

    this.cdr.detectChanges();
}

resetFilters(): void {
    this.selectedStudentFilter = null;
    this.selectedMatiereFilter = null;
    this.selectedTypeFilter = null;
    this.filteredNotes = [...this.notes];
    this.cdr.detectChanges();
}
}