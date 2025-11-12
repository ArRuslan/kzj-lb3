package com.my.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.my.dto.UserDTO;
import com.my.mapper.UserMapper;
import com.my.model.User;
import com.my.repository.UserRepository;

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
