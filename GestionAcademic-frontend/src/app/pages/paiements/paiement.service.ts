import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Paiement } from './paiement.model';

@Injectable({
    providedIn: 'root'
})
export class PaiementService {
    private apiUrl = 'http://localhost:8080/api/paiements';

    constructor(private http: HttpClient) {}

    getPaiements(): Observable<Paiement[]> {
        return this.http.get<Paiement[]>(this.apiUrl);
    }

    getPaiementById(id: number): Observable<Paiement> {
        return this.http.get<Paiement>(`${this.apiUrl}/${id}`);
    }

    addPaiement(paiement: Paiement): Observable<Paiement> {
        return this.http.post<Paiement>(this.apiUrl, paiement);
    }

    updatePaiement(id: number, paiement: Paiement): Observable<Paiement> {
        return this.http.put<Paiement>(`${this.apiUrl}/${id}`, paiement);
    }

    deletePaiement(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    getPaiementsByStudent(studentId: number): Observable<Paiement[]> {
        return this.http.get<Paiement[]>(`${this.apiUrl}/student/${studentId}`);
    }

    getPaiementsByFacture(factureId: number): Observable<Paiement[]> {
        return this.http.get<Paiement[]>(`${this.apiUrl}/facture/${factureId}`);
    }
}