import { CommonModule } from '@angular/common';
import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { FluidModule } from 'primeng/fluid';
import { SelectModule } from 'primeng/select';

import { Matiere } from './matiere.model';
import { MatiereService } from './matiere.service';
import { Filiere } from '../filieres/filiere.model';
import { FiliereService } from '../filieres/filiere.service';

@Component({
    selector: 'app-matiere-list',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        TableModule,
        ButtonModule,
        InputTextModule,
        TextareaModule,
        FluidModule,
        SelectModule
    ],
    templateUrl: './matiere-list.component.html',
    styleUrls: ['./matiere-list.component.scss']
})
export class MatiereListComponent implements OnInit {
    private matiereService = inject(MatiereService);
    private filiereService = inject(FiliereService);
    private cdr = inject(ChangeDetectorRef);

    matieres: Matiere[] = [];
    filieres: Filiere[] = [];

    matiere: Matiere = this.emptyMatiere();

    editMode = false;
    selectedId?: number;

    ngOnInit(): void {
        this.loadMatieres();
        this.loadFilieres();
    }

    loadMatieres(): void {
        this.matiereService.getMatieres().subscribe({
            next: (data) => {
                this.matieres = [...data];
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement matières : ', error);
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

    saveMatiere(): void {
        if (
            !this.matiere.nom.trim() ||
            !this.matiere.coefficient ||
            !this.matiere.semestre.trim() ||
            !this.matiere.filiere
        ) {
            return;
        }

        if (this.editMode && this.selectedId) {
            this.matiereService.updateMatiere(this.selectedId, this.matiere).subscribe({
                next: (updatedMatiere) => {
                    this.matieres = this.matieres.map((m) =>
                        m.id === this.selectedId ? updatedMatiere : m
                    );
                    this.resetForm();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur modification matière : ', error);
                }
            });
        } else {
            this.matiereService.addMatiere(this.matiere).subscribe({
                next: (newMatiere) => {
                    this.matieres = [...this.matieres, newMatiere];
                    this.resetForm();
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur ajout matière : ', error);
                }
            });
        }
    }

    editMatiere(matiere: Matiere): void {
        this.editMode = true;
        this.selectedId = matiere.id;
        this.matiere = { ...matiere };
        window.scrollTo({ top: 0, behavior: 'smooth' });
    }

    deleteMatiere(id: number): void {
        if (confirm('Voulez-vous supprimer cette matière ?')) {
            this.matiereService.deleteMatiere(id).subscribe({
                next: () => {
                    this.matieres = this.matieres.filter((m) => m.id !== id);
                    this.cdr.detectChanges();
                },
                error: (error) => {
                    console.error('Erreur suppression matière : ', error);
                }
            });
        }
    }

    resetForm(): void {
        this.matiere = this.emptyMatiere();
        this.editMode = false;
        this.selectedId = undefined;
    }

    refreshMatieres(): void {
        this.resetForm();
        this.loadMatieres();
    }

    private emptyMatiere(): Matiere {
        return {
            nom: '',
            description: '',
            coefficient: 1,
            semestre: '',
            filiere: null
        };
    }
}