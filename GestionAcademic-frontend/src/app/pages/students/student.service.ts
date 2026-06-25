import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Student } from './student.model';

@Injectable({ providedIn: 'root' })
export class StudentService {
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8080/api/students';

    getAll(): Observable<Student[]> {
        return this.http.get<Student[]>(this.apiUrl);
    }

    create(student: Student): Observable<Student> {
        return this.http.post<Student>(this.apiUrl, student);
    }

    update(id: number, student: Student): Observable<Student> {
        return this.http.put<Student>(`${this.apiUrl}/${id}`, student);
    }

    delete(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
    uploadPhoto(id: number, file: File): Observable<Student> {
    const formData = new FormData();
    formData.append('file', file);

    return this.http.post<Student>(`${this.apiUrl}/${id}/photo`, formData);
}
}