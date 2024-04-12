package com.cspi.commonsystem.user.service;

import java.util.List;

public interface UserGroupService {
    void linkUserWithGroups(String userId, List<Long> groupId);
}
