package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.user.dto.UserDTO;

import java.util.List;

public interface UserGroupService {
    void linkUserWithGroups(String userId, List<Long> groupId);
}
