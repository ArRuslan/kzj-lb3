package ua.nure.kz.dto;

import lombok.*;
import ua.nure.kz.model.User;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class UserDTO {
    private int id;
    private String login;
    private String fullName;
    private User.Role role;
    private Set<GroupDTO> groups;

    public boolean isAdmin() {
        return role == User.Role.ADMIN;
    }
}
