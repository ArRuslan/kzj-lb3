package ua.nure.kz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.model.User;
import ua.nure.kz.service.UserService;
import ua.nure.kz.utils.SessionUtil;

import java.security.NoSuchAlgorithmException;

@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("")
    public String usersList(
            Model model, HttpServletRequest request,
            @RequestParam(name = "page", defaultValue = "1", required = false) int page,
            @RequestParam(name = "pageSize", defaultValue = "10", required = false) int pageSize) {
        UserDTO user = SessionUtil.getUserFromSession(request, userService);
        if(user == null) {
            return "redirect:/auth/login";
        }

        model.addAttribute("user", user);
        model.addAttribute("users", userService.getUsers(page - 1, pageSize));

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
}
