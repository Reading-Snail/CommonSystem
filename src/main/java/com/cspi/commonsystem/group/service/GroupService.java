package com.cspi.commonsystem.group.service;

import com.cspi.commonsystem.group.domain.Group;
import com.cspi.commonsystem.group.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    GroupDTO addGroup(GroupDTO groupDTO);
    GroupDTO editGroup(GroupDTO groupDto);

    List<GroupDTO> getAllGroupDTO();

}
