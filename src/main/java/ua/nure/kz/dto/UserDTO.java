package ua.nure.kz.dto;

import lombok.*;
import ua.nure.kz.model.User;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class UserDTO {
    private long id;
    private String login;
    private String fullName;
    private User.Role role;
    private List<GroupDTO> groups;

    public boolean isAdmin() {
        return role == User.Role.ADMIN;
    }
}
