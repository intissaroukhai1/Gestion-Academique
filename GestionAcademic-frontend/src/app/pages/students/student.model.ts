import { Filiere } from '../filieres/filiere.model';

export interface Student {
    id?: number;
    lastName: string;
    firstName: string;
    cin: string;
    studentIdentifier: string;
    email: string;
    phone: string;
    program?: string;
    studyLevel: string;
    photo: string;
    address: string;
    filiere: Filiere | null;
}