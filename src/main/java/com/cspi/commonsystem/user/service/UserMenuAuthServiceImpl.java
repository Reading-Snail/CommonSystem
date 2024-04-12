package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.menu.domain.Menu;
import com.cspi.commonsystem.menu.repository.MenuRepository;
import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.domain.UserMenuAuth;
import com.cspi.commonsystem.user.repository.UserMenuAuthRepository;
import com.cspi.commonsystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class UserMenuAuthServiceImpl implements UserMenuAuthService{

    private final UserRepository userRepository;
    private final UserMenuAuthRepository userMenuAuthRepository;
    private final MenuRepository menuRepository;
    @Override
    public void createUserMenuAuth(UserMenuAuthDTO userMenuAuthDTO) {
        User user = userRepository.findById(userMenuAuthDTO.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userMenuAuthDTO.getUserId()));
        Menu menu = menuRepository.findById(userMenuAuthDTO.getMenuId())
                .orElseThrow(() -> new NoSuchElementException("Menu not found with id: " + userMenuAuthDTO.getMenuId()));

        UserMenuAuth userMenuAuth = userMenuAuthDTO.toEntity();


//        userMenuAuth.set()
        userMenuAuthRepository.save(userMenuAuth);

    }
}
