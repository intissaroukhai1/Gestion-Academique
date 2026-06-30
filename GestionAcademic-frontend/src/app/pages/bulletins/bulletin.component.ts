import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { ButtonModule } from 'primeng/button';
import { SelectModule } from 'primeng/select';
import { TableModule } from 'primeng/table';
import { FluidModule } from 'primeng/fluid';
import { TagModule } from 'primeng/tag';

import { Bulletin } from './bulletin.model';
import { BulletinService } from './bulletin.service';

import { Student } from '../students/student.model';
import { StudentService } from '../students/student.service';

@Component({
    selector: 'app-bulletin',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        ButtonModule,
        SelectModule,
        TableModule,
        FluidModule,
        TagModule
    ],
    templateUrl: './bulletin.component.html',
    styleUrls: ['./bulletin.component.scss']
})
export class BulletinComponent implements OnInit {
    private bulletinService = inject(BulletinService);
    private studentService = inject(StudentService);
    private cdr = inject(ChangeDetectorRef);

    students: Student[] = [];
    selectedStudent: Student | null = null;

    bulletin: Bulletin | null = null;

    loading = false;
    errorMessage = '';

    ngOnInit(): void {
        this.loadStudents();
    }

    loadStudents(): void {
        this.studentService.getAll().subscribe({
            next: (data) => {
                this.students = data;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement étudiants : ', error);
                this.errorMessage = 'Impossible de charger les étudiants.';
            }
        });
    }

    generateBulletin(): void {
        this.errorMessage = '';
        this.bulletin = null;

        if (!this.selectedStudent || !this.selectedStudent.id) {
            this.errorMessage = 'Veuillez choisir un étudiant.';
            return;
        }

        this.loading = true;

        this.bulletinService.getBulletinByStudent(this.selectedStudent.id).subscribe({
            next: (data) => {
                this.bulletin = data;
                this.loading = false;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement bulletin : ', error);
                this.errorMessage = 'Impossible de charger le bulletin.';
                this.loading = false;
                this.cdr.detectChanges();
            }
        });
    }

    printBulletin(): void {
        window.print();
    }

    getResultSeverity(resultat: string): 'success' | 'danger' | 'info' {
        if (resultat === 'Admis') {
            return 'success';
        }

        if (resultat === 'Ajourné') {
            return 'danger';
        }

        return 'info';
    }
}