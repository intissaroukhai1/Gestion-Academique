package org.example.gestionacademic.controller;

import org.example.gestionacademic.entity.Menu;
import org.example.gestionacademic.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menus")
@CrossOrigin(origins = "http://localhost:4200")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @GetMapping("/actifs")
    public List<Menu> getMenusActifs() {
        return menuService.getMenusActifs();
    }

    @GetMapping("/parents")
    public List<Menu> getMenusParents() {
        return menuService.getMenusParents();
    }

    @GetMapping("/parent/{parentId}")
    public List<Menu> getSousMenus(@PathVariable Long parentId) {
        return menuService.getSousMenus(parentId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        return ResponseEntity.ok(menuService.createMenu(menu));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(
            @PathVariable Long id,
            @RequestBody Menu menu
    ) {
        return ResponseEntity.ok(menuService.updateMenu(id, menu));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}