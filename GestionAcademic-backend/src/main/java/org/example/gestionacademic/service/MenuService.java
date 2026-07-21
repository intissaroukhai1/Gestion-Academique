package org.example.gestionacademic.service;

import org.example.gestionacademic.entity.Menu;
import org.example.gestionacademic.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public List<Menu> getMenusActifs() {
        return menuRepository.findByActifOrderByOrdreAsc("OUI");
    }

    public List<Menu> getMenusParents() {
        return menuRepository.findByParentIdIsNullAndActifOrderByOrdreAsc("OUI");
    }

    public List<Menu> getSousMenus(Long parentId) {
        return menuRepository.findByParentIdAndActifOrderByOrdreAsc(parentId, "OUI");
    }

    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu introuvable avec id : " + id));
    }

    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, Menu menu) {
        Menu existingMenu = getMenuById(id);

        existingMenu.setCodeMenu(menu.getCodeMenu());
        existingMenu.setLibelle(menu.getLibelle());
        existingMenu.setRoute(menu.getRoute());
        existingMenu.setIcon(menu.getIcon());
        existingMenu.setOrdre(menu.getOrdre());
        existingMenu.setLevel(menu.getLevel());
        existingMenu.setParentId(menu.getParentId());
        existingMenu.setActif(menu.getActif());
        existingMenu.setModificateur(menu.getModificateur());

        return menuRepository.save(existingMenu);
    }

    public void deleteMenu(Long id) {
        Menu menu = getMenuById(id);
        menuRepository.delete(menu);
    }
}