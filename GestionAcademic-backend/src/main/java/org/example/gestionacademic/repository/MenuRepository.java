package org.example.gestionacademic.repository;

import org.example.gestionacademic.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByActifOrderByOrdreAsc(String actif);

    List<Menu> findByParentIdAndActifOrderByOrdreAsc(Long parentId, String actif);

    List<Menu> findByParentIdIsNullAndActifOrderByOrdreAsc(String actif);
}