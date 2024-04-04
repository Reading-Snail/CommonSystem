package com.cspi.commonsystem.group.service;

import com.cspi.commonsystem.group.dto.GroupDTO;
import com.cspi.commonsystem.group.domain.Group;
import com.cspi.commonsystem.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;

    /**
     * 기존 그룹과 중복여부 확인 후, 신규 그룹 추가
     *
     * @param groupDto
     * @return
     */
    @Override
    public GroupDTO addGroup(GroupDTO groupDto) {
        // 그룹 ID 중복 확인
        Optional<Group> groupOptional = groupRepository.findById(groupDto.getId());
        groupOptional.ifPresent(group -> { throw new DuplicateKeyException("해당 그룹이 이미 존재합니다."); });
        // 그룹 저장
        return saveGroup(groupDto);
    }

    /**
     * 기존 그룹 존재 여부 확인 후, 기존 그룹 수정
     *
     * @param groupDto
     * @return
     */
    @Override
    public GroupDTO editGroup(GroupDTO groupDto) {
        // 기존 그룹 유효성 확인
        Optional<Group> groupOptional = groupRepository.findById(groupDto.getId());
        groupOptional.orElseThrow(() -> new NoSuchElementException("해당 그룹이 존재하지 않습니다."));
        // 그룹 저장
        return saveGroup(groupDto);
    }


    /**
     * 그룹 저장
     *
     * @param groupDto
     * @return
     */
    private GroupDTO saveGroup(GroupDTO groupDto) {
        // GroupDTO -> Group
        Group group = groupDto.toEntity();
        // 그룹 저장
        Group saved = groupRepository.save(group);
        // Group -> GroupDTO
        return modelMapper.map(saved, GroupDTO.class);
    }

    /**
     * 부모 Group 조회
     *
     * @param parentId 부모 id
     * @return 부모 Group
     * @exception IllegalArgumentException 부모 그룹을 찾을 수 없습니다.
     */
    private Group findParentById(Long parentId) {
        return groupRepository.findById(parentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 그룹 ("+ parentId +") 를 찾을 수 없습니다"));
    }


    @Override
    public List<GroupDTO> getAllGroupDTO() {
        return groupRepository.findAll()
                .stream()
                .map((group) -> modelMapper.map(group, GroupDTO.class))
                .collect(Collectors.toList());
    }
}