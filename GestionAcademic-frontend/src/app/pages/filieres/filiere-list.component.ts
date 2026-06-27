import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { FluidModule } from 'primeng/fluid';

import { Filiere } from './filiere.model';
import { FiliereService } from './filiere.service';

@Component({
    selector: 'app-filiere-list',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        TableModule,
        ButtonModule,
        InputTextModule,
        TextareaModule,
        FluidModule
    ],
    templateUrl: './filiere-list.component.html',
    styleUrls: ['./filiere-list.component.scss']
})
export class FiliereListComponent implements OnInit {
    private filiereService = inject(FiliereService);
    private cdr = inject(ChangeDetectorRef);

    filieres: Filiere[] = [];

    filiere: Filiere = {
        nom: '',
        description: ''
    };

    editMode = false;
    selectedId?: number;

    ngOnInit(): void {
        this.loadFilieres();
    }

    loadFilieres(): void {
        this.filiereService.getFilieres().subscribe({
            next: (data) => {
                this.filieres = [...data];
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement filières : ', error);
            }
        });
    }

    refreshFilieres(): void {
        this.resetForm();
        this.loadFilieres();
    }

    saveFiliere(): void {
        if (!this.filiere.nom.trim()) {
            return;
        }

        if (this.editMode && this.selectedId) {
            this.filiereService.updateFiliere(this.selectedId, this.filiere).subscribe({
                next: (updatedFiliere) => {
                    this.filieres = this.filieres.map((f) =>
                        f.id === this.selectedId ? updatedFiliere : f
                    );
                    this.resetForm();
                    this.cdr.detectChanges();
                }
            });
        } else {
            this.filiereService.addFiliere(this.filiere).subscribe({
                next: (newFiliere) => {
                    this.filieres = [...this.filieres, newFiliere];
                    this.resetForm();
                    this.cdr.detectChanges();
                }
            });
        }
    }

    editFiliere(filiere: Filiere): void {
        this.editMode = true;
        this.selectedId = filiere.id;
        this.filiere = { ...filiere };
    }

    deleteFiliere(id: number): void {
        if (confirm('Voulez-vous supprimer cette filière ?')) {
            this.filiereService.deleteFiliere(id).subscribe({
                next: () => {
                    this.filieres = this.filieres.filter((f) => f.id !== id);
                    this.cdr.detectChanges();
                }
            });
        }
    }

    resetForm(): void {
        this.filiere = {
            nom: '',
            description: ''
        };

        this.editMode = false;
        this.selectedId = undefined;
    }
}