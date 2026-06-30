import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bulletin } from './bulletin.model';

@Injectable({
    providedIn: 'root'
})
export class BulletinService {
    private apiUrl = 'http://localhost:8080/api/bulletins';

    constructor(private http: HttpClient) {}

    getBulletinByStudent(studentId: number): Observable<Bulletin> {
        return this.http.get<Bulletin>(`${this.apiUrl}/student/${studentId}`);
    }
}