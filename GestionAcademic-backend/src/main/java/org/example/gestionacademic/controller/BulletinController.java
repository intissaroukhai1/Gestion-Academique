package org.example.gestionacademic.controller;

import org.example.gestionacademic.dto.BulletinDTO;
import org.example.gestionacademic.service.BulletinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bulletins")
@CrossOrigin(origins = "http://localhost:4200")
public class BulletinController {

    private final BulletinService bulletinService;

    public BulletinController(BulletinService bulletinService) {
        this.bulletinService = bulletinService;
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<BulletinDTO> getBulletinByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(bulletinService.getBulletinByStudent(studentId));
    }
}