package com.cspi.commonsystem.user.service;

import com.cspi.commonsystem.group.domain.Group;
import com.cspi.commonsystem.group.repository.GroupRepository;
import com.cspi.commonsystem.user.domain.User;
import com.cspi.commonsystem.user.domain.UserGroup;
import com.cspi.commonsystem.user.dto.UserDTO;
import com.cspi.commonsystem.user.repository.UserGroupRepository;
import com.cspi.commonsystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserGroupServiceImpl implements UserGroupService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    /**
     * 사용자와 그룹을 연결하는 메서드
     *
     * @param userId  사용자 ID
     * @param groupIds 그룹 ID
     */
    public void linkUserWithGroups(String userId, List<Long> groupIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        List<Group> groups = groupRepository.findAllById(groupIds);

        // 이미 연결된 경우, 아무 작업도 수행하지 않음
        groups.forEach(group -> {
            if (!userGroupRepository.existsByUserAndGroup(user, group)) {
                UserGroup userGroup = new UserGroup();
                userGroup.setUserAndGroup(user, group);
                userGroupRepository.save(userGroup);
            }
        });
    }

}
