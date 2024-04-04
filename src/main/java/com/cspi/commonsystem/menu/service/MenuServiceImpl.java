package com.cspi.commonsystem.menu.service;

import com.cspi.commonsystem.menu.domain.Menu;
import com.cspi.commonsystem.menu.dto.MenuDTO;
import com.cspi.commonsystem.menu.dto.MenuTreeDTO;
import com.cspi.commonsystem.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    /**
     * 기존 메뉴와 중복여부 확인 후, 신규 메뉴 추가
     *
     * @param menuDto
     * @return
     */
    @Override
    public MenuDTO addMenu(MenuDTO menuDto) {
        // 메뉴 ID 중복 확인
        Optional<Menu> childOptional = menuRepository.findById(menuDto.getId());
        childOptional.ifPresent(child -> { throw new DuplicateKeyException("해당 메뉴가 이미 존재합니다."); });
        // 메뉴 저장
        return saveMenu(menuDto);
    }
    /**
     * 기존 메뉴 존재 여부 확인 후, 기존 메뉴 수정
     *
     * @param menuDto
     * @return
     */
    @Override
    public MenuDTO editMenu(MenuDTO menuDto) {
        // 기존 메뉴 유효성 확인
        Optional<Menu> childOptional = menuRepository.findById(menuDto.getId());
        childOptional.orElseThrow(() -> new NoSuchElementException("해당 메뉴가 존재하지 않습니다."));
        // 메뉴 저장
        return saveMenu(menuDto);
    }
    /**
     * 메뉴 저장
     *
     * @param menuDto
     * @return
     */
    private MenuDTO saveMenu(MenuDTO menuDto) {
        // MenuDTO -> Menu
        Menu child = menuDto.toEntity();
        // 부모 조회 및 저장
        Long parentId = menuDto.getParentId();
        if (parentId != null) {
            Menu parent = findParentById(parentId);
            child.addToParentAsChild(parent);
            menuRepository.save(parent);
        }
        // 자녀 저장
        Menu saved = menuRepository.save(child);
        // Menu -> MenuDTO
        return modelMapper.map(saved, MenuDTO.class);
    }
    /**
     * 부모 Menu 조회
     *
     * @param parentId 부모 id
     * @return 부모 Menu
     * @exception IllegalArgumentException 부모 메뉴를 찾을 수 없습니다.
     */
    private Menu findParentById(Long parentId) {
        return menuRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 메뉴 ("+ parentId +") 를 찾을 수 없습니다"));
    }

    /**
     * 전체 메뉴를 Tree 구조로 조회
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<MenuTreeDTO> getAllMenuTree() {
        return menuRepository.findAllByParentIsNull()
                .stream()
                .map(MenuTreeDTO::of)
                .collect(Collectors.toList());
    }
}
