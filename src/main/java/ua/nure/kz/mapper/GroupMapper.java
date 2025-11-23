package ua.nure.kz.mapper;

import org.modelmapper.ModelMapper;
import ua.nure.kz.dto.GroupDTO;
import ua.nure.kz.model.Group;

import java.util.List;
public class GroupMapper {

    private final ModelMapper modelMapper;

    public GroupMapper() {
        modelMapper = new ModelMapper();
		
		/*modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
			protected void configure() {
				map().setFullName(source.getName());
				map().setId(source.getId());
			}
		});*/
    }


    public GroupDTO toDTO(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }

    public Group toEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    public List<GroupDTO> toDTOList(List<Group> groups) {
        return groups.stream()
                .map(this::toDTO)
                .toList();
    }

}
