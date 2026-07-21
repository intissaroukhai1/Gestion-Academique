import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppMenuitem } from './app.menuitem';

import { MenuService } from '../../pages/menus/menu.service';
import { Menu } from '../../pages/menus/menu.model';

@Component({
    selector: 'app-menu',
    standalone: true,
    imports: [CommonModule, AppMenuitem],
    template: `
        <ul class="layout-menu">
            <ng-container *ngFor="let item of model">
                <li
                    app-menuitem
                    [item]="item"
                    [root]="true">
                </li>
            </ng-container>
        </ul>
    `
})
export class AppMenu implements OnInit {
    private menuService = inject(MenuService);
    private cdr = inject(ChangeDetectorRef);

    model: any[] = [];

    ngOnInit(): void {
        this.loadMenu();
          this.menuService.refreshMenu$.subscribe(() => {
        this.loadMenu();
    });
}
    

    loadMenu(): void {
        this.menuService.getMenusActifs().subscribe({
            next: (menus: Menu[]) => {
                this.model = [
                    {
                        label: 'HOME',
                        items: [
                            {
                                label: 'Dashboard',
                                icon: 'pi pi-fw pi-home',
                                routerLink: ['/'],
                                path: '/dashboard'
                            },
                            ...this.buildMenuTree(menus)
                        ]
                    }
                ];

                this.cdr.detectChanges();
            },
            error: (error: any) => {
                console.error('Erreur menu : ', error);
            }
        });
    }

    buildMenuTree(menus: Menu[]): any[] {
        const parents = menus
            .filter(menu => menu.parentId === null)
            .sort((a, b) => a.ordre - b.ordre);

        return parents.map(parent => {
            const parentPath = '/menu-' + parent.idMenu;

            const children = menus
                .filter(menu => menu.parentId === parent.idMenu)
                .sort((a, b) => a.ordre - b.ordre)
                .map(child => ({
                    label: child.libelle,
                    icon: child.icon,
                    routerLink: child.route ? [child.route] : undefined,
                    path: parentPath + '/menu-' + child.idMenu
                }));

            return {
                label: parent.libelle,
                icon: parent.icon,
                path: parentPath,
                items: children
            };
        });
    }
}