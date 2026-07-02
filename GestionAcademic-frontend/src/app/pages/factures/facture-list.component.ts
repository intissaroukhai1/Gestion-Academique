import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { FluidModule } from 'primeng/fluid';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';

import { Facture } from './facture.model';
import { FactureService } from './facture.service';
import { Student } from '../students/student.model';
import { StudentService } from '../students/student.service';

@Component({
    selector: 'app-facture-list',
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
    templateUrl: './facture-list.component.html',
    styleUrls: ['./facture-list.component.scss']
})
export class FactureListComponent implements OnInit {
    private factureService = inject(FactureService);
    private studentService = inject(StudentService);
    private cdr = inject(ChangeDetectorRef);

    factures: Facture[] = [];
    students: Student[] = [];

    facture: Facture = this.emptyFacture();

    editMode = false;
    selectedId: number | null = null;

    statuts = [
        { label: 'Non payée', value: 'NON_PAYEE' },
        { label: 'Payée', value: 'PAYEE' },
        { label: 'Partielle', value: 'PARTIELLE' }
    ];

    modesPaiement = [
        { label: 'Espèce', value: 'ESPECE' },
        { label: 'Carte', value: 'CARTE' },
        { label: 'Virement', value: 'VIREMENT' },
        { label: 'Chèque', value: 'CHEQUE' }
    ];

    ngOnInit(): void {
        this.loadFactures();
        this.loadStudents();
    }

    emptyFacture(): Facture {
        return {
            montant: 0,
            dateEmission: new Date().toISOString().substring(0, 10),
            statutPaiement: 'NON_PAYEE',
            modePaiement: 'ESPECE',
            student: null
        };
    }

    loadFactures(): void {
        this.factureService.getFactures().subscribe({
            next: (data) => {
                this.factures = [...data];
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement factures : ', error);
            }
        });
    }

    loadStudents(): void {
        this.studentService.getStudents().subscribe({
            next: (data) => {
                this.students = [...data];
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement étudiants : ', error);
            }
        });
    }

    saveFacture(): void {
        if (!this.facture.student) {
            alert('Veuillez choisir un étudiant');
            return;
        }

        if (!this.facture.montant || this.facture.montant <= 0) {
            alert('Veuillez saisir un montant valide');
            return;
        }

        if (this.editMode && this.selectedId) {
            this.factureService.updateFacture(this.selectedId, this.facture).subscribe({
                next: () => {
                    this.loadFactures();
                    this.resetForm();
                },
                error: (error) => {
                    console.error('Erreur modification facture : ', error);
                }
            });
        } else {
            this.factureService.addFacture(this.facture).subscribe({
                next: () => {
                    this.loadFactures();
                    this.resetForm();
                },
                error: (error) => {
                    console.error('Erreur ajout facture : ', error);
                }
            });
        }
    }

    editFacture(item: Facture): void {
        this.editMode = true;
        this.selectedId = item.id ?? null;

        this.facture = {
            id: item.id,
            numeroFacture: item.numeroFacture,
            montant: item.montant,
            dateEmission: item.dateEmission,
            statutPaiement: item.statutPaiement,
            modePaiement: item.modePaiement,
            student: item.student
        };
    }

    deleteFacture(id?: number): void {
        if (!id) {
            return;
        }

        if (confirm('Voulez-vous vraiment supprimer cette facture ?')) {
            this.factureService.deleteFacture(id).subscribe({
                next: () => {
                    this.loadFactures();
                },
                error: (error) => {
                    console.error('Erreur suppression facture : ', error);
                }
            });
        }
    }

    resetForm(): void {
        this.facture = this.emptyFacture();
        this.editMode = false;
        this.selectedId = null;
    }

    getStudentName(student: Student | null): string {
        if (!student) {
            return 'Non renseigné';
        }

        return `${student.firstName} ${student.lastName}`;
    }

getSeverity(statut: string): 'success' | 'danger' | 'warn' | 'info' {
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

    printFacture(item: Facture): void {
        const content = `
            <html>
                <head>
                    <title>Facture</title>
                    <style>
                        body { font-family: Arial, sans-serif; padding: 40px; }
                        .header { text-align: center; margin-bottom: 30px; }
                        .box { border: 1px solid #ddd; padding: 20px; border-radius: 8px; }
                        .row { margin-bottom: 12px; }
                        strong { display: inline-block; width: 180px; }
                    </style>
                </head>
                <body>
                    <div class="header">
                        <h1>Facture scolaire</h1>
                        <p>${item.numeroFacture ?? ''}</p>
                    </div>

                    <div class="box">
                        <div class="row"><strong>Étudiant :</strong> ${this.getStudentName(item.student)}</div>
                        <div class="row"><strong>Montant :</strong> ${item.montant} €</div>
                        <div class="row"><strong>Date émission :</strong> ${item.dateEmission}</div>
                        <div class="row"><strong>Statut :</strong> ${item.statutPaiement}</div>
                        <div class="row"><strong>Mode paiement :</strong> ${item.modePaiement}</div>
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