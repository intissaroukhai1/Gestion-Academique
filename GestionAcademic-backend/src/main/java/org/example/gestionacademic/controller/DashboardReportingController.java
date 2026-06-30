package org.example.gestionacademic.controller;

import org.example.gestionacademic.dto.DashboardReportingDTO;
import org.example.gestionacademic.service.DashboardReportingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
public class DashboardReportingController {

    private final DashboardReportingService dashboardReportingService;

    public DashboardReportingController(DashboardReportingService dashboardReportingService) {
        this.dashboardReportingService = dashboardReportingService;
    }

    @GetMapping("/reporting")
    public DashboardReportingDTO getReporting() {
        return dashboardReportingService.getReporting();
    }
}