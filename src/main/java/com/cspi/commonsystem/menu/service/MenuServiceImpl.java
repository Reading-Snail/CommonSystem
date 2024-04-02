package com.cspi.commonsystem.menu.service;

import com.cspi.commonsystem.menu.domain.Menu;
import com.cspi.commonsystem.menu.dto.MenuDTO;
import com.cspi.commonsystem.menu.dto.MenuTreeDTO;
import com.cspi.commonsystem.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    @Override
    public MenuDTO addMenu(MenuDTO menuDto) {
        // 필요한 필드들 설정
        Menu newNode = Menu.builder()
                .id(menuDto.getId())
                .build();

        // 부모 노드의 ID 가져오기
        String parentId = menuDto.getParentId();

        if (parentId != null) {
            // 부모 노드 조회
            Optional<Menu> parentOptional = menuRepository.findById(parentId);
            if (parentOptional.isPresent()) {
                Menu parent = parentOptional.get();

                // 부모-자식 관계 설정
                newNode.setParentAndAddToChildren(parent);
                menuRepository.save(parent);
            } else {
                // 부모 노드가 존재하지 않는 경우 처리
                throw new IllegalArgumentException("부모 노드를 찾을 수 없습니다: " + parentId);
            }
        }

        // 저장
        Menu saved = menuRepository.save(newNode);
        return modelMapper.map(saved, MenuDTO.class);
    }

    @Override
    public List<MenuTreeDTO> getAllMenuTree() {
        return menuRepository.findAllByParentIsNull().stream().map(MenuTreeDTO::of).collect(Collectors.toList());
    }
}
