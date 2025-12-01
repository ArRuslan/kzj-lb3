package ua.nure.kz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.kz.dto.GroupDTO;
import ua.nure.kz.mapper.GroupMapper;
import ua.nure.kz.model.Group;
import ua.nure.kz.model.User;
import ua.nure.kz.repository.GroupRepository;
import ua.nure.kz.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {
    private static final Sort SORT_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupMapper groupMapper;

    public GroupDTO findGroupByName(String login) {
        Group group = groupRepository.findGroupByName(login);
        return groupMapper.toDTO(group);
    }

    public GroupDTO findGroupById(long id) {
        Group group = groupRepository.findGroupById(id);
        return groupMapper.toDTO(group);
    }

    public long getGroupCount() {
        return groupRepository.count();
    }

    public List<GroupDTO> getGroups(int page, int pageSize) {
        return groupMapper.toDTOList(groupRepository.findAll(PageRequest.of(page, pageSize, SORT_BY_ID_ASC)).toList());
    }

    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(Group group) {
        deleteGroup(group.getId());
    }

    @Transactional
    public void deleteGroup(long groupId) {
        Group group = groupRepository.findGroupById(groupId);
        if (group == null) {
            return;
        }

        List<User> users = userRepository.findAllByGroupsContains(group);
        for (User user : users) {
            user.getGroups().remove(group);
        }

        groupRepository.delete(group);
    }
}
