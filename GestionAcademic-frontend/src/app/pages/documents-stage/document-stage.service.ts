import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DocumentStage } from './document-stage.model';

@Injectable({
    providedIn: 'root'
})
export class DocumentStageService {
    private apiUrl = 'http://localhost:8080/api/documents-stage';

    constructor(private http: HttpClient) {}

    getDocuments(): Observable<DocumentStage[]> {
        return this.http.get<DocumentStage[]>(this.apiUrl);
    }

    getDocumentById(id: number): Observable<DocumentStage> {
        return this.http.get<DocumentStage>(`${this.apiUrl}/${id}`);
    }

    addDocument(document: DocumentStage): Observable<DocumentStage> {
        return this.http.post<DocumentStage>(this.apiUrl, document);
    }

    updateDocument(id: number, document: DocumentStage): Observable<DocumentStage> {
        return this.http.put<DocumentStage>(`${this.apiUrl}/${id}`, document);
    }

    deleteDocument(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    validerDocument(id: number, commentaire: string): Observable<DocumentStage> {
        return this.http.put<DocumentStage>(
            `${this.apiUrl}/${id}/valider?commentaire=${encodeURIComponent(commentaire)}`,
            {}
        );
    }

    refuserDocument(id: number, commentaire: string): Observable<DocumentStage> {
        return this.http.put<DocumentStage>(
            `${this.apiUrl}/${id}/refuser?commentaire=${encodeURIComponent(commentaire)}`,
            {}
        );
    }

    getDocumentsByStudent(studentId: number): Observable<DocumentStage[]> {
        return this.http.get<DocumentStage[]>(`${this.apiUrl}/student/${studentId}`);
    }

    getDocumentsByStatut(statut: string): Observable<DocumentStage[]> {
        return this.http.get<DocumentStage[]>(`${this.apiUrl}/statut/${statut}`);
    }
    uploadDocument(formData: FormData): Observable<DocumentStage> {
    return this.http.post<DocumentStage>(`${this.apiUrl}/upload`, formData);
}

downloadDocument(id: number): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/${id}/download`, {
        responseType: 'blob'
    });
}
}