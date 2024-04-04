package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.dto.UserDTO;
import com.cspi.commonsystem.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class UserServiceImplUnitTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createUserWithExistingId() {
        User user1 = User.builder()
                .id("test1")
                .name("홍길동1")
                .email("test1@gmail.com")
                .password("1234")
                .prePasswords(User.PrePasswords.builder().build())
                .build();
        userRepository.save(user1);

        UserDTO userDto = UserDTO.builder()
                .id("test1")
                .name("홍길동2")
                .email("abcd@gmail.com")
                .password("1234")
                .departmentId("ABC")
                .companyId("C123")
                .build();

        assertThrows(DuplicateKeyException.class, () -> userService.createUser(userDto));
    }
    @Test
    void createUserWithNewId() {
        User user1 = User.builder()
                .id("test1")
                .name("홍길동1")
                .email("test1@gmail.com")
                .password("1234")
                .prePasswords(User.PrePasswords.builder().build())
                .build();
        userRepository.save(user1);

        UserDTO userDto = UserDTO.builder()
                .id("testNew")
                .name("홍길동New")
                .email("abcdNew@gmail.com")
                .password("1234New")
                .companyId("C123New")
                .build();
        UserDTO createdUser = userService.createUser(userDto);

        // 객체의 필드를 비교하여 같은지 확인
        assertThat(createdUser, samePropertyValuesAs(userDto));
    }
    @Test
    void editUserWithOutExistingId() {
        UserDTO userDto = UserDTO.builder()
                .id("test1")
                .name("홍길동new")
                .email("abcdNew@gmail.com")
                .password("1234New")
                .departmentId("ABCNew")
                .companyId("C123New")
                .build();
        assertThrows(NoSuchElementException.class, () -> userService.editUser(userDto));
    }
    @Test
    void editUserWithExistingId() {
        User oldUser = User.builder()
                .id("test1")
                .name("홍길동1")
                .password("1234")
                .email("test1@gmail.com")
                .departmentId("ABC")
                .companyId("C123")
                .build();
        userRepository.save(oldUser);

        UserDTO newUserDTO = UserDTO.builder()
                .id("test1")
                .name("홍길동new")
                .email("abcdNew@gmail.com")
                .password("1234New")
                .departmentId("ABCNew")
                .companyId("C123New")
                .build();

        assertThat(userService.editUser(newUserDTO), samePropertyValuesAs(newUserDTO));
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

        assertEquals(userRepository.findById("test1").get().getLockYn(),'N');
    }
}