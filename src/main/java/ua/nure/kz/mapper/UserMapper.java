package ua.nure.kz.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.model.User;

import java.util.Collection;
import java.util.List;

public class UserMapper {
    private final ModelMapper modelMapper;
    private final GroupMapper groupMapper;

    public UserMapper() {
        groupMapper = new GroupMapper();

        modelMapper = new ModelMapper();
        modelMapper.createTypeMap(User.class, UserDTO.class).setConverter(context -> {
            User source = context.getSource();
            return UserDTO.builder()
                    .id(source.getId())
                    .login(source.getLogin())
                    .fullName(source.getFullName())
                    .role(source.getRole())
                    .groups(groupMapper.toDTOList(source.getGroups()))
                    .build();
        });
    }

    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public List<UserDTO> toDTOList(Collection<User> users) {
        return users.stream()
                .map(this::toDTO)
                .toList();
    }

}
