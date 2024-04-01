package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto editUser(UserDto userDto);

    void changePassword(String userId, String newPassword, String newPasswordCheck);

    void lockUserIfFailAttemptExceedFive(String userId);

    void unlockUser(String userId);
}
