package com.cspi.commonsystem.menu.service;

import com.cspi.commonsystem.menu.dto.MenuDTO;
import com.cspi.commonsystem.menu.dto.MenuTreeDTO;

import java.util.List;

public interface MenuService {

    MenuDTO addMenu(MenuDTO menuDto);
    MenuDTO editMenu(MenuDTO menuDto);
    List<MenuTreeDTO> getAllMenuTree();
}
