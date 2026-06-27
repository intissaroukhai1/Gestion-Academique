import { Student } from '../students/student.model';
import { Matiere } from '../matieres/matiere.model';

export interface Note {
    id?: number;
    valeur: number;
    typeEvaluation: string;
    dateEvaluation: string;
    student: Student | null;
    matiere: Matiere | null;
}