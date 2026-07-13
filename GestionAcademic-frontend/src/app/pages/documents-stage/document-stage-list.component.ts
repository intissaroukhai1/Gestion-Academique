import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { TextareaModule } from 'primeng/textarea';
import { FluidModule } from 'primeng/fluid';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';

import { DocumentStage } from './document-stage.model';
import { DocumentStageService } from './document-stage.service';

import { Student } from '../students/student.model';
import { StudentService } from '../students/student.service';

@Component({
    selector: 'app-document-stage-list',
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
        TagModule
    ],
    templateUrl: './document-stage-list.component.html',
    styleUrls: ['./document-stage-list.component.scss']
})
export class DocumentStageListComponent implements OnInit {
    private documentStageService = inject(DocumentStageService);
    private studentService = inject(StudentService);
    private cdr = inject(ChangeDetectorRef);

    documents: DocumentStage[] = [];
    students: Student[] = [];

    documentStage: DocumentStage = this.emptyDocument();

    editMode = false;
    selectedId: number | null = null;
    selectedFile: File | null = null;

    typesDocuments = [
        { label: 'Convention de stage', value: 'CONVENTION_STAGE' },
        { label: 'Rapport de stage', value: 'RAPPORT_STAGE' },
        { label: 'Attestation de stage', value: 'ATTESTATION_STAGE' },
        { label: 'Fiche encadrement', value: 'FICHE_ENCADREMENT' }
    ];

    statuts = [
        { label: 'En attente', value: 'EN_ATTENTE' },
        { label: 'Validé', value: 'VALIDE' },
        { label: 'Refusé', value: 'REFUSE' }
    ];

    ngOnInit(): void {
        this.loadDocuments();
        this.loadStudents();
    }

    emptyDocument(): DocumentStage {
        return {
            titre: '',
            typeDocument: 'CONVENTION_STAGE',
            nomFichier: '',
            cheminFichier: '',
            dateDepot: new Date().toISOString().substring(0, 10),
            statut: 'EN_ATTENTE',
            commentaire: '',
            student: null
        };
    }

    loadDocuments(): void {
        this.documentStageService.getDocuments().subscribe({
            next: (data: DocumentStage[]) => {
                this.documents = [...data];
                this.cdr.detectChanges();
            },
            error: (error: any) => {
                console.error('Erreur chargement documents stage : ', error);
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

   saveDocument(): void {
    if (!this.documentStage.student) {
        alert('Veuillez choisir un étudiant');
        return;
    }

    if (!this.documentStage.titre.trim()) {
        alert('Veuillez saisir le titre du document');
        return;
    }

    if (this.editMode && this.selectedId) {
        this.documentStageService.updateDocument(this.selectedId, this.documentStage).subscribe({
            next: () => {
                this.loadDocuments();
                this.resetForm();
            },
            error: (error: any) => {
                console.error('Erreur modification document : ', error);
            }
        });

        return;
    }

    if (!this.selectedFile) {
        alert('Veuillez choisir un fichier PDF');
        return;
    }

    const formData = new FormData();
    formData.append('titre', this.documentStage.titre);
    formData.append('typeDocument', this.documentStage.typeDocument);
    formData.append('statut', this.documentStage.statut);
    formData.append('commentaire', this.documentStage.commentaire || '');
    formData.append('studentId', String(this.documentStage.student.id));
    formData.append('file', this.selectedFile);

    this.documentStageService.uploadDocument(formData).subscribe({
        next: () => {
            this.loadDocuments();
            this.resetForm();
        },
        error: (error: any) => {
            console.error('Erreur upload document : ', error);
        }
    });
}

    editDocument(item: DocumentStage): void {
        this.editMode = true;
        this.selectedId = item.id ?? null;

        this.documentStage = {
            id: item.id,
            titre: item.titre,
            typeDocument: item.typeDocument,
            nomFichier: item.nomFichier,
            cheminFichier: item.cheminFichier,
            dateDepot: item.dateDepot,
            statut: item.statut,
            commentaire: item.commentaire,
            student: item.student
        };
    }

    deleteDocument(id?: number): void {
        if (!id) {
            return;
        }

        if (confirm('Voulez-vous vraiment supprimer ce document ?')) {
            this.documentStageService.deleteDocument(id).subscribe({
                next: () => {
                    this.loadDocuments();
                },
                error: (error: any) => {
                    console.error('Erreur suppression document : ', error);
                }
            });
        }
    }

    valider(item: DocumentStage): void {
        const commentaire = prompt('Commentaire de validation :', 'Document accepté');

        this.documentStageService.validerDocument(item.id!, commentaire || '').subscribe({
            next: () => {
                this.loadDocuments();
            },
            error: (error: any) => {
                console.error('Erreur validation document : ', error);
            }
        });
    }

    refuser(item: DocumentStage): void {
        const commentaire = prompt('Motif de refus :', 'Document incomplet');

        this.documentStageService.refuserDocument(item.id!, commentaire || '').subscribe({
            next: () => {
                this.loadDocuments();
            },
            error: (error: any) => {
                console.error('Erreur refus document : ', error);
            }
        });
    }

    resetForm(): void {
        this.documentStage = this.emptyDocument();
        this.editMode = false;
        this.selectedId = null;
            this.selectedFile = null;

    }

    getStudentName(student: Student | null): string {
        if (!student) {
            return 'Non renseigné';
        }

        return `${student.firstName} ${student.lastName}`;
    }

    getTypeLabel(type: string): string {
        const found = this.typesDocuments.find(item => item.value === type);
        return found ? found.label : type;
    }

    getSeverity(statut: string): 'success' | 'danger' | 'warn' | 'info' {
        if (statut === 'VALIDE') {
            return 'success';
        }

        if (statut === 'REFUSE') {
            return 'danger';
        }

        if (statut === 'EN_ATTENTE') {
            return 'warn';
        }

        return 'info';
    }
    onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
        const file = input.files[0];

        if (file.type !== 'application/pdf') {
            alert('Veuillez choisir un fichier PDF');
            input.value = '';
            this.selectedFile = null;
            return;
        }

        this.selectedFile = file;
        this.documentStage.nomFichier = file.name;
    }
    
}
downloadDocument(item: DocumentStage): void {
    if (!item.id) {
        return;
    }

    this.documentStageService.downloadDocument(item.id).subscribe({
        next: (blob: Blob) => {
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');

            a.href = url;
            a.download = item.nomFichier || 'document-stage.pdf';
            a.click();

            window.URL.revokeObjectURL(url);
        },
        error: (error: any) => {
            console.error('Erreur téléchargement document : ', error);
        }
    });
}

}