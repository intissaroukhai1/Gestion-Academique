export interface BulletinMatiere {
    matiere: string;
    coefficient: number;
    note: number;
    typeEvaluation: string;
}

export interface Bulletin {
    studentId: number;
    nomComplet: string;
    cin: string;
    identifiant: string;
    email: string;
    filiere: string;
    niveau: string;
    moyenne: number;
    resultat: string;
    totalAbsences: number;
    notes: BulletinMatiere[];
}