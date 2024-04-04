package com.cspi.commonsystem.menu.service;

import com.cspi.commonsystem.menu.domain.Menu;
import com.cspi.commonsystem.menu.dto.MenuDTO;
import com.cspi.commonsystem.menu.dto.MenuTreeDTO;
import com.cspi.commonsystem.menu.repository.MenuRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
        long beforeCount = menuRepository.count();

        Menu menu = Menu.builder()
                .code("Parent")
                .parent(null)
                .build();
        menuRepository.save(menu);

        MenuDTO menuDTO2 = MenuDTO.builder()
                .code("Child")
                .parentId(1L)
                .build();
        menuService.addMenu(menuDTO2);

        long afterCount = menuRepository.count();
        assertEquals(afterCount-beforeCount, 2L);
    }



    @Test
    void getAllMenuTree() {
        List<MenuTreeDTO> menuTree = menuService.getAllMenuTree();
        // Gson 객체 생성
        Gson gson = new Gson();

        // List를 JSON으로 변환
        String json = gson.toJson(menuTree);

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        String prettyJson = prettyGson.toJson(jsonElement);

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(prettyJson);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }


}