package com.cspi.commonsystem.menu.service;

import com.cspi.commonsystem.menu.domain.Menu;
import com.cspi.commonsystem.menu.dto.MenuDTO;
import com.cspi.commonsystem.menu.dto.MenuTreeDTO;
import com.cspi.commonsystem.menu.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class MenuServiceImplTest {
    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void createMenu(){
        Menu menu = Menu.builder()
                .id("ABC-100")
                .parent(null)
                .build();
        menuRepository.save(menu);

        MenuDTO menuDTO2 = MenuDTO.builder()
                .id("ABC-512")
                .parentId("ABC-100")
                .build();
        menuService.addMenu(menuDTO2);

        long count = menuRepository.count();
        assertEquals(count, 2L);
    }

    @Test
    void getAllMenuTree() {
        Menu menu = Menu.builder()
                .id("ABC-100")
                .parent(null)
                .build();
        menuRepository.save(menu);

        MenuDTO menuDTO2 = MenuDTO.builder()
                .id("ABC-512")
                .parentId("ABC-100")
                .build();
        menuService.addMenu(menuDTO2);

        List<MenuTreeDTO> menuTree = menuService.getAllMenuTree();
        menuTree.forEach(System.out::println);
    }
}