package ua.nure.kz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ua.nure.kz.dto.UserDTO;
import ua.nure.kz.service.UserService;

@Controller // <-- @Component
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// http://localhost:8080/hello
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Hello, World!";
	}

	@RequestMapping("/test")
	@ResponseBody
	public String test(@RequestParam("login") String login) {
		UserDTO userDTO = userService.findUserByLogin(login); 
		return userDTO.toString();
	}

	// http://localhost:8080/test2 ==> /src/main/resources/templates/test2.html 
	@RequestMapping("/test2")
	public String test2(Model model, @RequestParam("login") String login) {
		UserDTO userDTO = userService.findUserByLogin(login); 
		model.addAttribute("user", userDTO);
		return "test2";
	}

	@RequestMapping("/find-all-users")
	public String findAllUsers(Model model) {
		List<UserDTO> users = userService.findAllUsers();
		model.addAttribute("users", users);
		return "users-list";
	}

}
