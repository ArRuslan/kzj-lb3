package ua.nure.kz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.model.Group;
import ua.nure.kz.model.User;
import ua.nure.kz.repository.GroupRepository;
import ua.nure.kz.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Sort SORT_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");
    private static final String GROUP_NAME_ADMINS = "Administrators";
    private static final String GROUP_NAME_USERS = "Users";

    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final UserMapper userMapper;

    public UserDTO findUserByLogin(String login) {
        User user = userRepository.findUserByLogin(login);
        return user == null ? null : userMapper.toDTO(user);
    }

    public UserDTO findUserById(long id) {
        User user = userRepository.findUserById(id);
        return user == null ? null : userMapper.toDTO(user);
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public List<UserDTO> getUsers(int page, int pageSize) {
        return userMapper.toDTOList(userRepository.findAll(PageRequest.of(page, pageSize, SORT_BY_ID_ASC)).toList());
    }

    @Transactional
    public User createUser(User user) {
        String groupName;
        if(user.getRole() == User.Role.ADMIN) {
            groupName = GROUP_NAME_ADMINS;
        } else {
            groupName = GROUP_NAME_USERS;
        }

        Group group = groupRepository.findGroupByName(groupName);
        if(group == null) {
            group = new Group();
            group.setName(groupName);
            group = groupRepository.save(group);
        }

        user.setGroups(new HashSet<>());
        user.getGroups().add(group);

        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUser(long userId) {
        userRepository.deleteById(userId);
    }
}
