import { Filiere } from '../filieres/filiere.model';

export interface Matiere {
    id?: number;
    nom: string;
    description: string;
    coefficient: number;
    semestre: string;
    filiere: Filiere | null;
}