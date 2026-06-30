import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface StatsByFiliere {
    filiere: string;
    total: number;
}

export interface MoyenneByFiliere {
    filiere: string;
    moyenne: number;
}

export interface AbsenceByMatiere {
    matiere: string;
    total: number;
}

export interface ProgressionStudent {
    student: string;
    moyenne: number;
    resultat: string;
}

export interface DashboardReporting {
    totalStudents: number;
    totalFilieres: number;
    totalMatieres: number;
    totalNotes: number;
    totalAbsences: number;
    totalPaiements: number;
    moyenneGenerale: number;
    tauxReussite: number;
    studentsByFiliere: StatsByFiliere[];
    moyenneByFiliere: MoyenneByFiliere[];
    absencesByMatiere: AbsenceByMatiere[];
    progressionAcademique: ProgressionStudent[];
}

@Injectable({
    providedIn: 'root'
})
export class DashboardService {
    private apiUrl = 'http://localhost:8080/api/dashboard/reporting';

    constructor(private http: HttpClient) {}

    getReporting(): Observable<DashboardReporting> {
        return this.http.get<DashboardReporting>(this.apiUrl);
    }
}