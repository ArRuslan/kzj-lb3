package ua.nure.kz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.model.Group;
import ua.nure.kz.model.User;
import ua.nure.kz.repository.GroupRepository;
import ua.nure.kz.repository.UserRepository;
import ua.nure.kz.service.UserService;
import ua.nure.kz.utils.SessionUtil;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("")
    public String usersList(
            Model model, HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(name = "groupId", defaultValue = "0", required = false) long groupId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }

        List<UserDTO> users;
        if(groupId > 0) {
            users = userService.getUsers(groupId, page - 1, pageSize);
        } else {
            users = userService.getUsers(page - 1, pageSize);
        }

        model.addAttribute("user", user);
        model.addAttribute("users", users);

        return "users-list";
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String createUser(
            Model model, HttpServletRequest request,
            @RequestParam("login") String login,
            @RequestParam("password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("role") User.Role role) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            model.addAttribute("error", "Insufficient privileges!");
            return "users-list";
        }

        User userToCreate = new User();
        userToCreate.setLogin(login);
        try {
            userToCreate.setPassword(User.hashPassword(password));
        } catch (NoSuchAlgorithmException e) {
            model.addAttribute("error", "Failed to create user!");
            return "users-list";
        }
        userToCreate.setFullName(fullName);
        userToCreate.setRole(role);

        userService.createUser(userToCreate);

        return "redirect:/users";
    }

    @GetMapping("edit/{userId}")
    public String editUserPage(Model model, HttpServletRequest request, @PathVariable(name = "userId") long userId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/users";
        }

        UserDTO targetUser = userService.findUserById(userId);
        if(targetUser == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", user);
        model.addAttribute("target", targetUser);

        return "users-edit";
    }

    @PostMapping(value = "edit/{userId}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String editUser(
            Model model, HttpServletRequest request,
            @PathVariable(name = "userId") long userId,
            @RequestParam("login") String login,
            @RequestParam("new_password") String password,
            @RequestParam("fullName") String fullName,
            @RequestParam("role") User.Role role) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/users";
        }

        User targetUser = userRepository.findUserById(userId);
        if(targetUser == null) {
            return "redirect:/users";
        }

        if(targetUser.getLogin().equals(login) && targetUser.getFullName().equals(fullName) && password.isEmpty() && targetUser.getRole() == role) {
            return "redirect:/users/edit/" + userId;
        }

        targetUser.setLogin(login);
        targetUser.setFullName(fullName);
        targetUser.setRole(role);
        if(!password.isEmpty()) {
            try {
                targetUser.setPassword(User.hashPassword(password));
            } catch (NoSuchAlgorithmException e) {
                model.addAttribute("error", "Failed to create user!");
                return "users-list";
            }
        }

        userService.updateUser(targetUser);

        return "redirect:/users/edit/" + userId;
    }

    @GetMapping("delete/{userId}")
    public String deleteUserPage(Model model, HttpServletRequest request, @PathVariable(name = "userId") long userId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/users";
        }

        UserDTO targetUser = userService.findUserById(userId);
        if(targetUser == null) {
            return "redirect:/users";
        }

        model.addAttribute("user", user);
        model.addAttribute("target", targetUser);

        return "users-delete";
    }

    @PostMapping("delete/{userId}")
    public String deleteUser(HttpServletRequest request, @PathVariable(name = "userId") long userId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/users";
        }

        userService.deleteUser(userId);
        return "redirect:/users";
    }

    @PostMapping(value = "add-group", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addGroupToUser(
            Model model, HttpServletRequest request,
            @RequestParam("userId") long userId,
            @RequestParam("groupName") String groupName) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/users";
        }

        User targetUser = userRepository.findUserById(userId);
        if(targetUser == null) {
            return "redirect:/users";
        }

        Group group = groupRepository.findGroupByName(groupName);
        if(group == null) {
            model.addAttribute("error", "Unknown group!");
            model.addAttribute("user", user);
            model.addAttribute("target", targetUser);
            return "users-edit";
        }

        targetUser.getGroups().add(group);
        userService.updateUser(targetUser);

        return "redirect:/users/edit/" + userId;
    }

    @PostMapping(value = "remove-group", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String removeGroupFromUser(
            Model model, HttpServletRequest request,
            @RequestParam("userId") long userId,
            @RequestParam("groupId") long groupId) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }
        if(!user.isAdmin()) {
            return "redirect:/users";
        }

        User targetUser = userRepository.findUserById(userId);
        if(targetUser == null) {
            return "redirect:/users";
        }

        Group group = groupRepository.findGroupById(groupId);
        if(group == null) {
            model.addAttribute("error", "Unknown group!");
            model.addAttribute("user", user);
            model.addAttribute("target", targetUser);
            return "users-edit";
        }

        if(targetUser.getGroups().remove(group)) {
            userService.updateUser(targetUser);
        }

        return "redirect:/users/edit/" + userId;
    }
}
