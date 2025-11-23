package ua.nure.kz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.model.User;
import ua.nure.kz.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Sort SORT_BY_ID_ASC = Sort.by(Sort.Direction.ASC, "id");

    private final UserRepository userRepository;
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

    public User createUser(User user) {
//        try {
//            user.setPassword(User.hashPassword(user.getPassword()));
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }

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
