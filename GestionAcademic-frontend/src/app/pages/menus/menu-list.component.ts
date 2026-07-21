import { Component, OnInit ,ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TableModule } from 'primeng/table';
import { ButtonModule } from 'primeng/button';
import { DialogModule } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { SelectModule } from 'primeng/select';
import { TagModule } from 'primeng/tag';

import { MenuService } from './menu.service';
import { Menu } from './menu.model';

@Component({
    selector: 'app-menu-list',
    standalone: true,
    imports: [
        CommonModule,
        FormsModule,
        TableModule,
        ButtonModule,
        DialogModule,
        InputTextModule,
        SelectModule,
        TagModule
    ],
    template: `
        <div class="card">
            <div class="flex justify-between items-center mb-4">
                <h2>Gestion des rubriques</h2>

                <button
                    pButton
                    label="Ajouter rubrique"
                    icon="pi pi-plus"
                    (click)="openNew()">
                </button>
            </div>

            <p-table [value]="menus" [paginator]="true" [rows]="10" responsiveLayout="scroll">
                <ng-template pTemplate="header">
                    <tr>
                        <th>Code</th>
                        <th>Libellé</th>
                        <th>Type</th>
                        <th>Route</th>
                        <th>Icône</th>
                        <th>Ordre</th>
                        <th>Parent</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </ng-template>

                <ng-template pTemplate="body" let-menu>
                    <tr>
                        <td>{{ menu.codeMenu }}</td>
                        <td>{{ menu.libelle }}</td>
                        <td>
                            <p-tag
                                [value]="menu.parentId === null ? 'Rubrique' : 'Sous-rubrique'"
                                [severity]="menu.parentId === null ? 'info' : 'success'">
                            </p-tag>
                        </td>
                        <td>{{ menu.route || '-' }}</td>
                        <td>
                            <i [ngClass]="menu.icon"></i>
                            {{ menu.icon }}
                        </td>
                        <td>{{ menu.ordre }}</td>
                        <td>{{ getParentName(menu.parentId) }}</td>
                        <td>
                            <p-tag
                                [value]="menu.actif"
                                [severity]="menu.actif === 'OUI' ? 'success' : 'danger'">
                            </p-tag>
                        </td>
                        <td>
                            <button
                                pButton
                                icon="pi pi-pencil"
                                class="p-button-rounded p-button-text"
                                (click)="editMenu(menu)">
                            </button>

                            <button
                                pButton
                                icon="pi pi-trash"
                                class="p-button-rounded p-button-text p-button-danger"
                                (click)="deleteMenu(menu)">
                            </button>
                        </td>
                    </tr>
                </ng-template>
            </p-table>
        </div>

        <p-dialog
            [(visible)]="menuDialog"
            [style]="{ width: '500px' }"
            header="Rubrique"
            [modal]="true">

            <div class="formgrid grid">

                <div class="field col-12">
                    <label>Type</label>
                    <p-select
                        [options]="typeOptions"
                        [(ngModel)]="selectedType"
                        optionLabel="label"
                        optionValue="value"
                        placeholder="Choisir type"
                        (onChange)="onTypeChange()"
                        class="w-full">
                    </p-select>
                </div>

                <div class="field col-12">
                    <label>Code menu</label>
                    <input pInputText type="number" [(ngModel)]="menu.codeMenu" class="w-full" />
                </div>

                <div class="field col-12">
                    <label>Libellé</label>
                    <input pInputText [(ngModel)]="menu.libelle" class="w-full" />
                </div>

                <div class="field col-12" *ngIf="selectedType === 'SOUS_RUBRIQUE'">
                    <label>Rubrique parent</label>
                    <p-select
                        [options]="parentMenus"
                        [(ngModel)]="menu.parentId"
                        optionLabel="libelle"
                        optionValue="idMenu"
                        placeholder="Choisir rubrique parent"
                        class="w-full">
                    </p-select>
                </div>

                <div class="field col-12">
                    <label>Route</label>
                    <input
                        pInputText
                        [(ngModel)]="menu.route"
                        class="w-full"
                        placeholder="/students ou vide pour rubrique principale" />
                </div>

                <div class="field col-12">
                    <label>Icône</label>
                    <input
                        pInputText
                        [(ngModel)]="menu.icon"
                        class="w-full"
                        placeholder="pi pi-fw pi-book" />
                </div>

                <div class="field col-12">
                    <label>Ordre</label>
                    <input pInputText type="number" [(ngModel)]="menu.ordre" class="w-full" />
                </div>

                <div class="field col-12">
                    <label>Statut</label>
                    <p-select
                        [options]="statutOptions"
                        [(ngModel)]="menu.actif"
                        optionLabel="label"
                        optionValue="value"
                        class="w-full">
                    </p-select>
                </div>
            </div>

            <ng-template pTemplate="footer">
                <button
                    pButton
                    label="Annuler"
                    icon="pi pi-times"
                    class="p-button-text"
                    (click)="hideDialog()">
                </button>

                <button
                    pButton
                    label="Enregistrer"
                    icon="pi pi-check"
                    (click)="saveMenu()">
                </button>
            </ng-template>
        </p-dialog>
    `
})
export class MenuListComponent implements OnInit {
    menus: Menu[] = [];
    parentMenus: Menu[] = [];

    menuDialog = false;
    editMode = false;

    selectedType: 'RUBRIQUE' | 'SOUS_RUBRIQUE' = 'RUBRIQUE';

    menu: Menu = this.getEmptyMenu();

    typeOptions = [
        { label: 'Rubrique principale', value: 'RUBRIQUE' },
        { label: 'Sous-rubrique', value: 'SOUS_RUBRIQUE' }
    ];

    statutOptions = [
        { label: 'Actif', value: 'OUI' },
        { label: 'Inactif', value: 'NON' }
    ];

constructor(
    private menuService: MenuService,
    private cdr: ChangeDetectorRef
) {}
    ngOnInit(): void {
        this.loadMenus();
    }

  loadMenus(): void {
    this.menuService.getMenus().subscribe({
        next: (data) => {
            setTimeout(() => {
                this.menus = data.sort((a, b) => a.ordre - b.ordre);

                this.parentMenus = data.filter(
                    menu => menu.parentId === null && menu.actif === 'OUI'
                );

                this.cdr.detectChanges();
            });
        },
        error: (error) => {
            console.error('Erreur chargement menus : ', error);
        }
    });
}

    openNew(): void {
        this.menu = this.getEmptyMenu();
        this.selectedType = 'RUBRIQUE';
        this.editMode = false;
        this.menuDialog = true;
    }

    editMenu(menu: Menu): void {
        this.menu = { ...menu };
        this.selectedType = menu.parentId === null ? 'RUBRIQUE' : 'SOUS_RUBRIQUE';
        this.editMode = true;
        this.menuDialog = true;
    }

    saveMenu(): void {
        if (!this.menu.libelle || !this.menu.codeMenu) {
            alert('Veuillez remplir le code et le libellé.');
            return;
        }

        if (this.selectedType === 'RUBRIQUE') {
            this.menu.parentId = null;
            this.menu.level = 1;
            this.menu.route = this.menu.route || null;
        } else {
            if (!this.menu.parentId) {
                alert('Veuillez choisir une rubrique parent.');
                return;
            }

            this.menu.level = 2;
        }

        if (!this.menu.icon) {
            this.menu.icon = 'pi pi-fw pi-circle';
        }

        if (!this.menu.actif) {
            this.menu.actif = 'OUI';
        }

        if (!this.menu.createur) {
            this.menu.createur = 'admin';
        }

        if (this.editMode && this.menu.idMenu) {
            this.menuService.updateMenu(this.menu.idMenu, this.menu).subscribe({
                next: () => {
                    this.loadMenus();
                    this.menuService.notifyMenuRefresh();
                    this.hideDialog();
                },
                error: (error) => {
                    console.error('Erreur modification menu : ', error);
                }
            });
        } else {
            this.menuService.createMenu(this.menu).subscribe({
                next: () => {
                    this.loadMenus();
                      this.menuService.notifyMenuRefresh();
                    this.hideDialog();
                },
                error: (error) => {
                    console.error('Erreur ajout menu : ', error);
                }
            });
        }
    }

    deleteMenu(menu: Menu): void {
        if (!menu.idMenu) return;

        const hasChildren = this.menus.some(m => m.parentId === menu.idMenu);

        if (hasChildren) {
            alert('Impossible de supprimer cette rubrique car elle contient des sous-rubriques.');
            return;
        }

        if (confirm('Voulez-vous supprimer cette rubrique ?')) {
            this.menuService.deleteMenu(menu.idMenu).subscribe({
              next: () => {
    this.loadMenus();
    this.menuService.notifyMenuRefresh();
},
                error: (error) => {
                    console.error('Erreur suppression menu : ', error);
                }
            });
        }
    }

    hideDialog(): void {
        this.menuDialog = false;
    }

    onTypeChange(): void {
        if (this.selectedType === 'RUBRIQUE') {
            this.menu.parentId = null;
            this.menu.level = 1;
            this.menu.route = null;
        } else {
            this.menu.level = 2;
        }
    }

    getParentName(parentId: number | null): string {
        if (parentId === null) {
            return '-';
        }

        const parent = this.menus.find(menu => menu.idMenu === parentId);
        return parent ? parent.libelle : '-';
    }

    getEmptyMenu(): Menu {
        return {
            codeMenu: 0,
            libelle: '',
            route: null,
            icon: 'pi pi-fw pi-circle',
            ordre: 1,
            level: 1,
            parentId: null,
            actif: 'OUI',
            createur: 'admin'
        };
    }
}