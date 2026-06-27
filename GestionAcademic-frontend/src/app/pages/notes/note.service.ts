import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Note } from './note.model';

@Injectable({
    providedIn: 'root'
})
export class NoteService {
    private apiUrl = 'http://localhost:8080/api/notes';

    constructor(private http: HttpClient) {}

    getNotes(): Observable<Note[]> {
        return this.http.get<Note[]>(this.apiUrl);
    }

    addNote(note: Note): Observable<Note> {
        return this.http.post<Note>(this.apiUrl, note);
    }

    updateNote(id: number, note: Note): Observable<Note> {
        return this.http.put<Note>(`${this.apiUrl}/${id}`, note);
    }

    deleteNote(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}