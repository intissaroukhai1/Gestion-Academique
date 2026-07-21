import { Routes } from '@angular/router';
import { AppLayout } from './app/layout/component/app.layout';
import { Dashboard } from './app/pages/dashboard/dashboard';
import { Documentation } from './app/pages/documentation/documentation';
import { Landing } from './app/pages/landing/landing';
import { Notfound } from './app/pages/notfound/notfound';


export const appRoutes: Routes = [
      {
        path: '',
        component: AppLayout,
        children: [
            { path: '', component: Dashboard },

            {
                path: 'students',
                loadComponent: () =>
                    import('./app/pages/students/student-list.component')
                        .then(m => m.StudentListComponent)
            },


  {
        path: 'filieres',
        loadComponent: () =>
          import('./app/pages/filieres/filiere-list.component')
            .then(m => m.FiliereListComponent)
      },
      {
    path: 'matieres',
    loadComponent: () =>
        import('./app/pages/matieres/matiere-list.component')
            .then(m => m.MatiereListComponent)
},
{
    path: 'notes',
    loadComponent: () =>
        import('./app/pages/notes/note-list.component')
            .then(m => m.NoteListComponent)
},
{
    path: 'absences',
    loadComponent: () =>
        import('./app/pages/absences/absence-list.component')
            .then(m => m.AbsenceListComponent)
},
{
    path: 'bulletins',
    loadComponent: () =>
        import('./app/pages/bulletins/bulletin.component')
            .then(m => m.BulletinComponent)
},
{
    path: 'factures',
    loadComponent: () =>
        import('./app/pages/factures/facture-list.component')
            .then(m => m.FactureListComponent)
},
{
    path: 'paiements',
    loadComponent: () =>
        import('./app/pages/paiements/paiement-list.component')
            .then(m => m.PaiementListComponent)
},
{
    path: 'documents-stage',
    loadComponent: () =>
        import('./app/pages/documents-stage/document-stage-list.component')
            .then(m => m.DocumentStageListComponent)
},
{
    path: 'menus',
    loadComponent: () =>
        import('./app/pages/menus/menu-list.component').then(m => m.MenuListComponent)
},
            { path: 'uikit', loadChildren: () => import('./app/pages/uikit/uikit.routes') },
            { path: 'documentation', component: Documentation },
            { path: 'pages', loadChildren: () => import('./app/pages/pages.routes') }
        ]
    },
    { path: 'landing', component: Landing },
    { path: 'notfound', component: Notfound },
    { path: 'auth', loadChildren: () => import('./app/pages/auth/auth.routes') },
    { path: '**', redirectTo: '/notfound' }
];
