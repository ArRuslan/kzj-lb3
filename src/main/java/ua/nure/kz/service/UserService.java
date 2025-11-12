package ua.nure.kz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.model.User;
import ua.nure.kz.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	
	public UserDTO findUserByLogin(String login) {
		User user = userRepository.findUserByLogin(login) ;
		return userMapper.toDTO(user);
	}

	public List<UserDTO> findAllUsers() {
		return userMapper.toDTOList(userRepository.findAll());
	}

}
