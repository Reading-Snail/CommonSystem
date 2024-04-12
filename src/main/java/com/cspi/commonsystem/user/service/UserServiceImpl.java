package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.dto.UserReqDTO;
import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.dto.UserRespDTO;
import com.cspi.commonsystem.user.repository.UserRepository;
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
    private final UserGroupService userGroupService;
    private final ModelMapper modelMapper;

    /**
     * 사용자 정보를 받아와 기존 아이디 존재 여부 확인 후, 사용자 생성
     *
     * @param userReqDto 생성될 사용자 정보를 담고 있는 DTO
     * @return
     */
    public UserRespDTO createUser(UserReqDTO userReqDto){
        Optional<User> existUser = userRepository.findById(userReqDto.getId());
        if(existUser.isPresent()){
            throw new DuplicateKeyException("이미 존재하는 아이디 입니다.");
        }else{
            // 사용자 생성
            User user = userReqDto.toEntity();
            User savedUser = userRepository.save(user);

            // 생성된 사용자 그룹 정보 연결
            userGroupService.linkUserWithGroups(savedUser.getId(), userReqDto.getGroupIds());

            return UserRespDTO.toRespDTO(savedUser);
        }
    }
    /**
     * 사용자 정보를 수정하는 메서드
     *
     * @param userReqDto 수정될 사용자 정보를 담고 있는 DTO
     * @return
     */
    public UserRespDTO editUser(UserReqDTO userReqDto){
        userRepository.findById(userReqDto.getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자 입니다."));
        User editUser = userReqDto.toEntity();
        User editedUser = userRepository.save(editUser);
        userGroupService.linkUserWithGroups(editedUser.getId(), userReqDto.getGroupIds());
        return UserRespDTO.toRespDTO(editedUser);
    }

    @Override
    public void changePassword(String userId, String password) {
        if(userRepository.existsByIdAndNewPassword(userId, password)){
            throw new DuplicateKeyException("이전에 사용한 비밀번호는 사용하실 수 없습니다.");
        }
        User user = verfiyAndGetUser(userId);
        user.changePassword(password);
    }

    /**
     * 사용자 계정을 잠그는 메서드
     *
     * @param userId 사용자ID
     */
    public void lockUserIfFailAttemptExceedFive(String userId){
        User user = verfiyAndGetUser(userId);
        if(user.getFailAttempt()>5) user.lockUser();
    }

    /**
     * 사용자 계정을 잠금 해제하는 메서드
     *
     * @param userId 잠금 해제할 사용자의 id 값
     */
    public void unlockUser(String userId){
        User user = verfiyAndGetUser(userId);
        user.unlockUser();
    }

    private User verfiyAndGetUser(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자 입니다."));
    }


}
