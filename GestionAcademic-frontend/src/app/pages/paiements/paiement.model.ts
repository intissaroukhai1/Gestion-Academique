import { Student } from '../students/student.model';
import { Facture } from '../factures/facture.model';

export interface Paiement {
    id?: number;
    montant: number;
    datePaiement: string;
    modePaiement: string;
    facture: Facture | null;
    student: Student | null;
}