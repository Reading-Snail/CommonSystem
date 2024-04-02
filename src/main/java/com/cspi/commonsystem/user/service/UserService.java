package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.dto.UserDTO;

public interface UserService {

    UserDTO createUser(UserDTO userDto);

    UserDTO editUser(UserDTO userDto);

    void changePassword(String userId, String newPassword);

    void lockUserIfFailAttemptExceedFive(String userId);

    void unlockUser(String userId);
}
