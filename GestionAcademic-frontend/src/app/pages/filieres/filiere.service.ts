import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Filiere } from './filiere.model';

@Injectable({
  providedIn: 'root'
})
export class FiliereService {
  private apiUrl = 'http://localhost:8080/api/filieres';

  constructor(private http: HttpClient) {}

  getFilieres(): Observable<Filiere[]> {
    return this.http.get<Filiere[]>(this.apiUrl);
  }

  addFiliere(filiere: Filiere): Observable<Filiere> {
    return this.http.post<Filiere>(this.apiUrl, filiere);
  }

  updateFiliere(id: number, filiere: Filiere): Observable<Filiere> {
    return this.http.put<Filiere>(`${this.apiUrl}/${id}`, filiere);
  }

  deleteFiliere(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}