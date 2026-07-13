import { Student } from '../students/student.model';

export interface DocumentStage {
    id?: number;
    titre: string;
    typeDocument: string;
    nomFichier: string;
    cheminFichier: string;
    dateDepot: string;
    statut: string;
    commentaire: string;
    student: Student | null;
}