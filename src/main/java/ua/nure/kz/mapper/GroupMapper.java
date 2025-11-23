package ua.nure.kz.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import ua.nure.kz.dto.GroupDTO;
import ua.nure.kz.model.Group;

import java.util.Collection;
import java.util.List;

public class GroupMapper {

    private final ModelMapper modelMapper;

    public GroupMapper() {
        modelMapper = new ModelMapper();

        modelMapper.addMappings(new PropertyMap<Group, GroupDTO>() {
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
            }
        });
    }

    public GroupDTO toDTO(Group group) {
        return modelMapper.map(group, GroupDTO.class);
    }

    public Group toEntity(GroupDTO groupDTO) {
        return modelMapper.map(groupDTO, Group.class);
    }

    public List<GroupDTO> toDTOList(Collection<Group> groups) {
        return groups.stream()
                .map(this::toDTO)
                .toList();
    }

}
