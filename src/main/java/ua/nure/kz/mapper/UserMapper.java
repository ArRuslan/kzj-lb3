package ua.nure.kz.mapper;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.model.User;

public class UserMapper {
	
	private final ModelMapper modelMapper;

	public UserMapper() {
		modelMapper = new ModelMapper();
		
		modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
			protected void configure() {
				map().setFullName(source.getName());
				map().setId(source.getId());
			}
		});
	}
	
	
	public UserDTO toDTO(User user) {
		return modelMapper.map(user, UserDTO.class);
	}

	public User toEntity(UserDTO userDTO) {
		return modelMapper.map(userDTO, User.class);
	}

	public List<UserDTO> toDTOList(List<User> users) {
		return users.stream()
				.map(this::toDTO)
				.toList();
	}

}
