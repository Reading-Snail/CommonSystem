package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.group.domain.Group;
import com.cspi.commonsystem.group.repository.GroupRepository;
import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.dto.UserReqDTO;
import com.cspi.commonsystem.user.dto.UserRespDTO;
import com.cspi.commonsystem.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    /**
     * 이미 존재하는 아이디가 있을 경우 유효성 검사
     */
    @Test
    void createUser_WithExistingUserId() {
        User user1 = User.builder()
                .id("test1")
                .name("홍길동1")
                .email("test1@gmail.com")
                .password("1234")
                .prePasswords(User.PrePasswords.builder().build())
                .build();
        userRepository.save(user1);

        UserReqDTO userReqDto = UserReqDTO.builder()
                .id("test1")
                .name("홍길동2")
                .email("abcd@gmail.com")
                .password("1234")
                .departmentId("ABC")
                .companyId("C123")
                .build();

        assertThrows(DuplicateKeyException.class, () -> userService.createUser(userReqDto));
    }
    @Test
    void createUser_WithNewUserId() {
        User user1 = User.builder()
                .id("test1")
                .name("홍길동1")
                .email("test1@gmail.com")
                .password("1234")
                .prePasswords(User.PrePasswords.builder().build())
                .build();
        userRepository.save(user1);

        UserReqDTO userReqDto = UserReqDTO.builder()
                .id("testNew")
                .name("홍길동New")
                .email("abcdNew@gmail.com")
                .password("1234New")
                .companyId("C123New")
                .build();
        UserRespDTO userRespDTO = userService.createUser(userReqDto);

        // 객체의 필드를 비교하여 같은지 확인
        assertEquals(userReqDto.getId(), userRespDTO.getId());
        assertEquals(userReqDto.getName(), userRespDTO.getName());
        assertEquals(userReqDto.getEmail(), userRespDTO.getEmail());
        assertEquals(userReqDto.getFailAttempt(), userRespDTO.getFailAttempt());
        assertEquals(userReqDto.getLatestLogin(), userRespDTO.getLatestLogin());
        assertEquals(userReqDto.getDepartmentId(), userRespDTO.getDepartmentId());
        assertEquals(userReqDto.getCompanyId(), userRespDTO.getCompanyId());
        assertEquals(userReqDto.getGroupIds(), userRespDTO.getGroupIds());
    }
    @Test
    void createUser_WithNewUserIdAndGroupIds() {
        Group group1 = Group.builder()
                .code("GRP000001")
                .name("group1")
                .build();
        Group savedGroup = groupRepository.save(group1);

        UserReqDTO userReqDto = UserReqDTO.builder()
                .id("user2")
                .groupIds(Collections.singletonList(savedGroup.getId()))
                .name("홍길동New")
                .email("abcdNew@gmail.com")
                .password("1234")
                .companyId("C123")
                .build();
        UserRespDTO userRespDTO = userService.createUser(userReqDto);

        // 객체의 필드를 비교하여 같은지 확인
        assertEquals(userReqDto.getId(), userRespDTO.getId());
        assertEquals(userReqDto.getName(), userRespDTO.getName());
        assertEquals(userReqDto.getEmail(), userRespDTO.getEmail());
        assertEquals(userReqDto.getFailAttempt(), userRespDTO.getFailAttempt());
        assertEquals(userReqDto.getLatestLogin(), userRespDTO.getLatestLogin());
        assertEquals(userReqDto.getDepartmentId(), userRespDTO.getDepartmentId());
        assertEquals(userReqDto.getCompanyId(), userRespDTO.getCompanyId());
        assertEquals(userReqDto.getGroupIds(), userRespDTO.getGroupIds());
    }

    @Test
    void editUserWithOutExistingId() {
        UserReqDTO userReqDto = UserReqDTO.builder()
                .id("test1")
                .name("홍길동new")
                .email("abcdNew@gmail.com")
                .password("1234New")
                .departmentId("ABCNew")
                .companyId("C123New")
                .build();
        assertThrows(NoSuchElementException.class, () -> userService.editUser(userReqDto));
    }
    @Test
    void editUserWithExistingId() {
        Group group1 = Group.builder()
                .code("GRP000002")
                .name("group2")
                .build();
        Group savedGroup1 = groupRepository.save(group1);

        User user1 = User.builder()
                .id("user1")
                .name("홍길동1")
                .email("test1@gmail.com")
                .password("1234")
                .prePasswords(User.PrePasswords.builder().build())
                .build();
        userRepository.save(user1);

        UserReqDTO userReqDto = UserReqDTO.builder()
                .id("user1")
                .name("홍길동2")
                .email("abcdNew@gmail.com")
                .password("1234New")
                .groupIds(Collections.singletonList(savedGroup1.getId()))
                .departmentId("ABCNew")
                .companyId("C123New")
                .build();

        UserRespDTO userRespDTO = userService.editUser(userReqDto);
        // 객체의 필드를 비교하여 같은지 확인
        assertEquals(userReqDto.getId(), userRespDTO.getId());
        assertEquals(userReqDto.getName(), userRespDTO.getName());
        assertEquals(userReqDto.getEmail(), userRespDTO.getEmail());
        assertEquals(userReqDto.getFailAttempt(), userRespDTO.getFailAttempt());
        assertEquals(userReqDto.getLatestLogin(), userRespDTO.getLatestLogin());
        assertEquals(userReqDto.getDepartmentId(), userRespDTO.getDepartmentId());
        assertEquals(userReqDto.getCompanyId(), userRespDTO.getCompanyId());
        assertEquals(userReqDto.getGroupIds(), userRespDTO.getGroupIds());
    }
    @Test
    void changePassword_WhenNewPasswordIsProvided() {
        User user1 = User.builder()
                .id("test1")
                .name("홍길동1")
                .password("test")
                .email("test1@gmail.com")
                .prePasswords(User.PrePasswords.builder()
                        .prePassword1("A2#h7Fg@")
                        .prePassword2("tY5=K*3s")
                        .prePassword3("d9Q^@4vR")
                        .prePassword4("L0P&r@2E")
                        .prePassword5("6jS@9hGt")
                        .build()
                )
                .build();
        userRepository.save(user1);
        userService.changePassword("test1","Xy7@K2pQ");
        assertEquals(userRepository.findById("test1").get().getPassword(), "Xy7@K2pQ");
    }
    @Test
    void changePassword_WhenNewPasswordIsDuplicated() {
        User user1 = User.builder()
                .id("test1")
                .name("홍길동1")
                .password("test")
                .email("test1@gmail.com")
                .prePasswords(User.PrePasswords.builder()
                        .prePassword1("A2#h7Fg@")
                        .prePassword2("tY5=K*3s")
                        .prePassword3("d9Q^@4vR")
                        .prePassword4("L0P&r@2E")
                        .prePassword5("6jS@9hGt")
                        .build()
                )
                .build();
        userRepository.save(user1);
        assertThrows(DuplicateKeyException.class,
                () -> userService.changePassword("test1","A2#h7Fg@")
        );
    }
    @Test
    void lockUser_WhenFailAttemptIsSix_ThenLockYnIsY() {
        User user = User.builder()
                .id("test1")
                .name("홍길동1")
                .password("1234")
                .email("test1@gmail.com")
                .failAttempt(6)
                .build();
        userRepository.save(user);

        userService.lockUserIfFailAttemptExceedFive("test1");

        assertEquals(userRepository.findById("test1").get().getLockYn(),'Y');
    }
    @Test
    void lockUser_WhenFailAttemptIsFive_ThenLockYnIsN() {
        User user = User.builder()
                .id("test1")
                .name("홍길동1")
                .password("1234")
                .email("test1@gmail.com")
                .failAttempt(5)
                .build();
        userRepository.save(user);

        userService.lockUserIfFailAttemptExceedFive("test1");

        assertEquals('N',userRepository.findById("test1").get().getLockYn());
    }
}