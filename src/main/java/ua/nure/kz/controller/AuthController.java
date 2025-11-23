package ua.nure.kz.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.kz.mapper.UserMapper;
import ua.nure.kz.model.User;
import ua.nure.kz.repository.UserRepository;
import ua.nure.kz.service.UserService;

@Controller
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/login")
    public String loginGet(HttpServletRequest request) {
        return "login";
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String loginPost(Model model, HttpServletRequest request, @RequestParam("login") String login, @RequestParam("password") String password) {
        User user = userRepository.findUserByLogin(login);
        if(user == null) {
            model.addAttribute("error", "Unknown user or password is incorrect!");
            return "login";
        }

        if(!user.checkPassword(password)) {
            model.addAttribute("error", "Unknown user or password is incorrect!");
            return "login";
        }

        return "redirect:/users";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:login";
    }
}
