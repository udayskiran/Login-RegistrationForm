package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Login;
import com.example.demo.model.User;
import com.example.demo.service.LoginService;
import com.example.demo.service.UserService;

@Controller
public class IndexController {

	@Autowired
	public UserService userService;
	@Autowired
	LoginService loginService;

	@GetMapping("/")
	public String loginForm() {
		return "loginForm";
	}

	@PostMapping("/login")
	public String userLogin(@ModelAttribute Login login, Model model) {

		Login loginInserted = loginService.loginDetails(login);
		if (loginInserted != null) {
			model.addAttribute("StudentName", login.getEmail());
			return "dashboard";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "loginForm";
		}

	}

	@GetMapping("/api")
	public String registrationForm() {
		return "index";
	}

	@PostMapping("/api/register")
	public String userRegistration(@ModelAttribute User user, Model model) {

		if (user.getEmail() != null && !userService.isEmailUnique(user.getEmail())) {
			model.addAttribute("error", "This email is already registered. Please use a different email.");
			return "index";
		} else {
			userService.saveData(user);
			model.addAttribute("firstName", user.getFirstName());
			return "welcome";
		}

	}
}
