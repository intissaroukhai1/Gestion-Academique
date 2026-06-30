import { Student } from '../students/student.model';
import { Matiere } from '../matieres/matiere.model';

export interface Absence {
    id?: number;
    dateAbsence: string;
    justifiee: boolean;
    motif: string;
    student: Student | null;
    matiere: Matiere | null;
}