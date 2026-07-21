import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Menu } from './menu.model';

@Injectable({
    providedIn: 'root'
})
export class MenuService {
    private apiUrl = 'http://localhost:8080/api/menus';

    private refreshMenuSource = new Subject<void>();
    refreshMenu$ = this.refreshMenuSource.asObservable();

    constructor(private http: HttpClient) {}

    getMenus(): Observable<Menu[]> {
        return this.http.get<Menu[]>(this.apiUrl);
    }

    getMenusActifs(): Observable<Menu[]> {
        return this.http.get<Menu[]>(`${this.apiUrl}/actifs`);
    }

    createMenu(menu: Menu): Observable<Menu> {
        return this.http.post<Menu>(this.apiUrl, menu);
    }

    updateMenu(id: number, menu: Menu): Observable<Menu> {
        return this.http.put<Menu>(`${this.apiUrl}/${id}`, menu);
    }

    deleteMenu(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }

    notifyMenuRefresh(): void {
        this.refreshMenuSource.next();
    }
}