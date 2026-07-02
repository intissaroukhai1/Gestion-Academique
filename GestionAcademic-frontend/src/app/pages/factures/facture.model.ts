import { Student } from '../students/student.model';

export interface Facture {
    id?: number;
    numeroFacture?: string;
    montant: number;
    dateEmission: string;
    statutPaiement: string;
    modePaiement: string;
    student: Student | null;
}