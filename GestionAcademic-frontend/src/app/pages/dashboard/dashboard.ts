import { Component, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ChartModule } from 'primeng/chart';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag';

import { DashboardService, DashboardReporting } from './dashboard.service';

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [CommonModule, ChartModule, ButtonModule, TagModule],
    templateUrl: './dashboard.html',
    styleUrls: ['./dashboard.scss']
})
export class Dashboard implements OnInit {
    private dashboardService = inject(DashboardService);
    private cdr = inject(ChangeDetectorRef);

    reporting: DashboardReporting | null = null;

    cards: any[] = [];

    studentsByFiliereChart: any;
    moyenneByFiliereChart: any;
    absencesByMatiereChart: any;
    progressionChart: any;

    chartOptions: any;

    loading = false;

    ngOnInit(): void {
        this.initChartOptions();
        this.loadReporting();
    }

    loadReporting(): void {
        this.loading = true;

        this.dashboardService.getReporting().subscribe({
            next: (data) => {
                this.reporting = data;
                this.prepareCards();
                this.prepareCharts();
                this.loading = false;
                this.cdr.detectChanges();
            },
            error: (error) => {
                console.error('Erreur chargement reporting : ', error);
                this.loading = false;
                this.cdr.detectChanges();
            }
        });
    }

    prepareCards(): void {
        if (!this.reporting) {
            return;
        }

        this.cards = [
            {
                label: 'Étudiants',
                value: this.reporting.totalStudents,
                icon: 'pi pi-users',
                color: 'blue',
                subtitle: 'Total inscrits'
            },
            {
                label: 'Filières',
                value: this.reporting.totalFilieres,
                icon: 'pi pi-sitemap',
                color: 'green',
                subtitle: 'Parcours disponibles'
            },
            {
                label: 'Matières',
                value: this.reporting.totalMatieres,
                icon: 'pi pi-book',
                color: 'purple',
                subtitle: 'Unités enseignées'
            },
            {
                label: 'Notes',
                value: this.reporting.totalNotes,
                icon: 'pi pi-star',
                color: 'orange',
                subtitle: 'Évaluations saisies'
            },
            {
                label: 'Absences',
                value: this.reporting.totalAbsences,
                icon: 'pi pi-calendar-times',
                color: 'red',
                subtitle: 'Absences enregistrées'
            },
            {
                label: 'Paiements',
                value: this.reporting.totalPaiements,
                icon: 'pi pi-credit-card',
                color: 'cyan',
                subtitle: 'Paiements suivis'
            },
            {
                label: 'Moyenne générale',
                value: this.reporting.moyenneGenerale + '/20',
                icon: 'pi pi-chart-line',
                color: 'indigo',
                subtitle: 'Performance globale'
            },
            {
                label: 'Taux réussite',
                value: this.reporting.tauxReussite + '%',
                icon: 'pi pi-check-circle',
                color: 'teal',
                subtitle: 'Étudiants admis'
            }
        ];
    }

    prepareCharts(): void {
        if (!this.reporting) {
            return;
        }

        this.studentsByFiliereChart = {
            labels: this.reporting.studentsByFiliere.map(item => item.filiere),
            datasets: [
                {
                    label: 'Étudiants',
                    data: this.reporting.studentsByFiliere.map(item => item.total),
                    borderRadius: 12
                }
            ]
        };

        this.moyenneByFiliereChart = {
            labels: this.reporting.moyenneByFiliere.map(item => item.filiere),
            datasets: [
                {
                    label: 'Moyenne /20',
                    data: this.reporting.moyenneByFiliere.map(item => item.moyenne),
                    borderRadius: 12
                }
            ]
        };

        this.absencesByMatiereChart = {
            labels: this.reporting.absencesByMatiere.map(item => item.matiere),
            datasets: [
                {
                    data: this.reporting.absencesByMatiere.map(item => item.total)
                }
            ]
        };

        this.progressionChart = {
            labels: this.reporting.progressionAcademique.map(item => item.student),
            datasets: [
                {
                    label: 'Moyenne étudiant',
                    data: this.reporting.progressionAcademique.map(item => item.moyenne),
                    tension: 0.4,
                    fill: false
                }
            ]
        };
    }

    initChartOptions(): void {
        this.chartOptions = {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: {
                    labels: {
                        usePointStyle: true
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {
                        drawBorder: false
                    }
                },
                x: {
                    grid: {
                        display: false
                    }
                }
            }
        };
    }

    getSeverity(resultat: string): 'success' | 'danger' | 'info' {
        if (resultat === 'Admis') {
            return 'success';
        }

        if (resultat === 'Ajourné') {
            return 'danger';
        }

        return 'info';
    }
}