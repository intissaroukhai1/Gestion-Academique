import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Matiere } from './matiere.model';

@Injectable({
    providedIn: 'root'
})
export class MatiereService {
    private apiUrl = 'http://localhost:8080/api/matieres';

    constructor(private http: HttpClient) {}

    getMatieres(): Observable<Matiere[]> {
        return this.http.get<Matiere[]>(this.apiUrl);
    }

    addMatiere(matiere: Matiere): Observable<Matiere> {
        return this.http.post<Matiere>(this.apiUrl, matiere);
    }

    updateMatiere(id: number, matiere: Matiere): Observable<Matiere> {
        return this.http.put<Matiere>(`${this.apiUrl}/${id}`, matiere);
    }

    deleteMatiere(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}