package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.dto.UserReqDTO;
import com.cspi.commonsystem.user.dto.UserRespDTO;

public interface UserService {

    UserRespDTO createUser(UserReqDTO userReqDto);

    UserRespDTO editUser(UserReqDTO userReqDto);

    void changePassword(String userId, String newPassword);

    void lockUserIfFailAttemptExceedFive(String userId);

    void unlockUser(String userId);
}
