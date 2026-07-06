import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FluidModule } from 'primeng/fluid';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';

import { Paiement } from './paiement.model';
import { PaiementService } from './paiement.service';

import { Student } from '../students/student.model';
import { StudentService } from '../students/student.service';

import { Facture } from '../factures/facture.model';
import { FactureService } from '../factures/facture.service';

@Component({
    selector: 'app-paiement-list',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        TableModule,
        ButtonModule,
        InputTextModule,
        FluidModule,
        SelectModule,
        TagModule
    ],
    templateUrl: './paiement-list.component.html',
    styleUrls: ['./paiement-list.component.scss']
})
export class PaiementListComponent implements OnInit {
    private paiementService = inject(PaiementService);
    private studentService = inject(StudentService);
    private factureService = inject(FactureService);
    private cdr = inject(ChangeDetectorRef);

    paiements: Paiement[] = [];
    students: Student[] = [];
    factures: Facture[] = [];

    paiement: Paiement = this.emptyPaiement();

    editMode = false;
    selectedId: number | null = null;

    modesPaiement = [
        { label: 'Espèce', value: 'ESPECE' },
        { label: 'Carte', value: 'CARTE' },
        { label: 'Virement', value: 'VIREMENT' },
        { label: 'Chèque', value: 'CHEQUE' }
    ];

    ngOnInit(): void {
        this.loadPaiements();
        this.loadStudents();
        this.loadFactures();
    }

    emptyPaiement(): Paiement {
        return {
            montant: 0,
            datePaiement: new Date().toISOString().substring(0, 10),
            modePaiement: 'ESPECE',
            facture: null,
            student: null
        };
    }

    loadPaiements(): void {
        this.paiementService.getPaiements().subscribe({
            next: (data: Paiement[]) => {
                this.paiements = [...data];
                this.cdr.detectChanges();
            },
            error: (error: any) => {
                console.error('Erreur chargement paiements : ', error);
            }
        });
    }

    loadStudents(): void {
        this.studentService.getStudents().subscribe({
            next: (data: Student[]) => {
                this.students = [...data];
                this.cdr.detectChanges();
            },
            error: (error: any) => {
                console.error('Erreur chargement étudiants : ', error);
            }
        });
    }

    loadFactures(): void {
        this.factureService.getFactures().subscribe({
            next: (data: Facture[]) => {
                this.factures = [...data];
                this.cdr.detectChanges();
            },
            error: (error: any) => {
                console.error('Erreur chargement factures : ', error);
            }
        });
    }

    savePaiement(): void {
        if (!this.paiement.student) {
            alert('Veuillez choisir un étudiant');
            return;
        }

        if (!this.paiement.facture) {
            alert('Veuillez choisir une facture');
            return;
        }

        if (!this.paiement.montant || this.paiement.montant <= 0) {
            alert('Veuillez saisir un montant valide');
            return;
        }

        if (this.editMode && this.selectedId) {
            this.paiementService.updatePaiement(this.selectedId, this.paiement).subscribe({
                next: () => {
                    this.loadPaiements();
                    this.loadFactures();
                    this.resetForm();
                },
                error: (error: any) => {
                    console.error('Erreur modification paiement : ', error);
                }
            });
        } else {
            this.paiementService.addPaiement(this.paiement).subscribe({
                next: () => {
                    this.loadPaiements();
                    this.loadFactures();
                    this.resetForm();
                },
                error: (error: any) => {
                    console.error('Erreur ajout paiement : ', error);
                }
            });
        }
    }

    editPaiement(item: Paiement): void {
        this.editMode = true;
        this.selectedId = item.id ?? null;

        this.paiement = {
            id: item.id,
            montant: item.montant,
            datePaiement: item.datePaiement,
            modePaiement: item.modePaiement,
            facture: item.facture,
            student: item.student
        };
    }

    deletePaiement(id?: number): void {
        if (!id) {
            return;
        }

        if (confirm('Voulez-vous vraiment supprimer ce paiement ?')) {
            this.paiementService.deletePaiement(id).subscribe({
                next: () => {
                    this.loadPaiements();
                    this.loadFactures();
                },
                error: (error: any) => {
                    console.error('Erreur suppression paiement : ', error);
                }
            });
        }
    }

    resetForm(): void {
        this.paiement = this.emptyPaiement();
        this.editMode = false;
        this.selectedId = null;
    }

    getStudentName(student: Student | null): string {
        if (!student) {
            return 'Non renseigné';
        }

        return `${student.firstName} ${student.lastName}`;
    }

    getFactureLabel(facture: Facture | null): string {
        if (!facture) {
            return 'Non renseignée';
        }

        return `${facture.numeroFacture} - ${facture.montant} €`;
    }

    getStatutSeverity(statut: string): 'success' | 'danger' | 'warn' | 'info' {
        if (statut === 'PAYEE') {
            return 'success';
        }

        if (statut === 'NON_PAYEE') {
            return 'danger';
        }

        if (statut === 'PARTIELLE') {
            return 'warn';
        }

        return 'info';
    }
    printFacture(item: Paiement): void {
    if (!item.facture) {
        return;
    }

    const facture = item.facture;

    const content = `
        <html>
            <head>
                <title>Facture</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        padding: 40px;
                        color: #1e293b;
                    }
                    .header {
                        text-align: center;
                        margin-bottom: 30px;
                    }
                    .box {
                        border: 1px solid #ddd;
                        padding: 20px;
                        border-radius: 8px;
                    }
                    .row {
                        margin-bottom: 12px;
                    }
                    strong {
                        display: inline-block;
                        width: 180px;
                    }
                </style>
            </head>
            <body>
                <div class="header">
                    <h1>Facture scolaire</h1>
                    <p>${facture.numeroFacture ?? ''}</p>
                </div>

                <div class="box">
                    <div class="row">
                        <strong>Étudiant :</strong>
                        ${this.getStudentName(item.student)}
                    </div>
                    <div class="row">
                        <strong>Montant total :</strong>
                        ${facture.montant ?? 0} €
                    </div>
                    <div class="row">
                        <strong>Date émission :</strong>
                        ${facture.dateEmission ?? ''}
                    </div>
                    <div class="row">
                        <strong>Statut :</strong>
                        ${facture.statutPaiement ?? ''}
                    </div>
                    <div class="row">
                        <strong>Mode paiement :</strong>
                        ${item.modePaiement ?? ''}
                    </div>
                    <div class="row">
                        <strong>Montant payé :</strong>
                        ${item.montant ?? 0} €
                    </div>
                    <div class="row">
                        <strong>Date paiement :</strong>
                        ${item.datePaiement ?? ''}
                    </div>
                </div>
            </body>
        </html>
    `;

    const printWindow = window.open('', '_blank');

    if (printWindow) {
        printWindow.document.write(content);
        printWindow.document.close();
        printWindow.print();
    }
}
}
