import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Facture } from './facture.model';

@Injectable({
    providedIn: 'root'
})
export class FactureService {
    private apiUrl = 'http://localhost:8080/api/factures';

    constructor(private http: HttpClient) {}

    getFactures(): Observable<Facture[]> {
        return this.http.get<Facture[]>(this.apiUrl);
    }

    getFactureById(id: number): Observable<Facture> {
        return this.http.get<Facture>(`${this.apiUrl}/${id}`);
    }

    addFacture(facture: Facture): Observable<Facture> {
        return this.http.post<Facture>(this.apiUrl, facture);
    }

    updateFacture(id: number, facture: Facture): Observable<Facture> {
        return this.http.put<Facture>(`${this.apiUrl}/${id}`, facture);
    }

    deleteFacture(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    getFacturesByStudent(studentId: number): Observable<Facture[]> {
        return this.http.get<Facture[]>(`${this.apiUrl}/student/${studentId}`);
    }

    getFacturesByStatut(statut: string): Observable<Facture[]> {
        return this.http.get<Facture[]>(`${this.apiUrl}/statut/${statut}`);
    }
}