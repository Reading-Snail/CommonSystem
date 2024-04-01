package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.exception.PasswordMismatchException;
import com.cspi.commonsystem.user.dto.UserDto;
import com.cspi.commonsystem.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    /**
     * 사용자 정보를 받아와 기존 아이디 존재여부를 확인 한 후, 사용자 생성
     *
     * @param userDto 생성될 사용자 정보를 담고 있는 DTO
     * @return
     */
    public UserDto createUser(UserDto userDto){
        Optional<User> existUser = userRepository.findById(userDto.getId());
        if(existUser.isPresent()){
            throw new DuplicateKeyException("이미 존재하는 아이디 입니다.");
        }else{
            User newUser = userDto.toEntity();

            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + newUser.getId());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + newUser.getName());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + newUser.getEmail());
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@" + newUser.getPassword());

            User saved = userRepository.save(newUser);
            return modelMapper.map(saved, UserDto.class);
        }
    }
    /**
     * 사용자 정보를 수정하는 메서드
     *
     * @param userDto 수정될 사용자 정보를 담고 있는 DTO
     * @return
     */
    public UserDto editUser(UserDto userDto){
        verifyUserExist(userDto.getId());
        User editUser = userDto.toEntity();
        User editedUser = userRepository.save(editUser);
        return modelMapper.map(editedUser, UserDto.class);
    }

    @Override
    public void changePassword(String userId, String password, String confirmPassword) {
        matchConfirmPassword(password, confirmPassword);
        User user = verifyUserExist(userId);
        user.changePassword(password);
        userRepository.save(user);
    }
    private void matchConfirmPassword(String password, String confirmPassword){
        if(!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("비밀번호와 비밀번호확인이 일치하지 않습니다.");
        }
    }

    /**
     * 사용자 계정을 잠그는 메서드
     *
     * @param userId
     */
    public void lockUserIfFailAttemptExceedFive(String userId){
        User user = verifyUserExist(userId);
        if(user.getFailAttempt()>5) {
            user.lockUser();
            userRepository.save(user);
        }
    }

    /**
     * 사용자 계정을 잠금 해제하는 메서드
     *
     * @param userId 잠금 해제할 사용자의 id 값
     */
    public void unlockUser(String userId){
        User user = verifyUserExist(userId);
        user.unlockUser();
        userRepository.save(user);
    }

    private User verifyUserExist(String userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자 입니다."));
    }
}
