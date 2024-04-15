package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.dto.LoginDto;
import com.example.demo.entity.Login;
import com.example.demo.entity.User;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.service.UserService;

@Controller
public class IndexController {

	@Autowired
	public UserService userService;
	@Autowired
	public LoginService loginService;
	@Autowired
	EmailService mailService;

	@GetMapping("/")
	public String loginForm() {
		return "loginForm";
	}

	@PostMapping("/login")
	public String userLogin(@ModelAttribute Login login, Model model) {

		Login loginInserted = loginService.loginDetails(login);
		if (loginInserted != null) {
			model.addAttribute("Name", login.getEmail());
			return "dashboard";
		} else {
			model.addAttribute("error", "Invalid username or password");
			return "loginForm";
		}

	}
	@GetMapping("/forget")
	public String emailForm() {
		return "EmailOtp";
	}
	@PostMapping("/forget/sendEmail")
	public String sendEmail(@ModelAttribute LoginDto user, Model model) {

		String message= loginService.forgotPasswordSendMail(user.getEmail());
		if (user.getEmail() != null && message=="invalid") {
			model.addAttribute("error", "Invalid email id. Please use a different email.");
			return "EmailOtp";
		} else {
			return "verifyEmail";
		}
	}
	@PostMapping("/forget/sendEmail/verifyEmail")
	public String verifyEmail(@ModelAttribute LoginDto user, Model model) {

		String message= loginService.forgetPasswordVerifyEmail(user.getEmail(),user.getOtp(),user.getNewPassword(),user.getConfPassword());
		if (user.getEmail() != null && message=="invalid") {
			model.addAttribute("error", "Invalid email id.! Please use a different email.");
			return "verifyEmail";
		} else if(message=="incorrect"){
			model.addAttribute("error", "Invalid otp.! Please enter correct otp");
			return "verifyEmail";
		}else if(message=="notMatched"){
			model.addAttribute("error", "New passwords not matched.!");
			return "verifyEmail";
		}
		String htmlFile=userService.readHtmlContent("templates/success.html");
		mailService.sendMail(user.getEmail(),"Password Changed Successfully",htmlFile);
		return "success";
	}

	@GetMapping("/api")
	public String registrationForm() {
		return "index";
	}
	
	@PostMapping("/api/register")
	public String userRegistration(@ModelAttribute User user, Model model) {

		User userData= userService.saveData(user);
		if (user.getEmail() != null && userData==null) {
			model.addAttribute("error", "This email is already registered. Please use a different email.");
			return "index";
		} else {
			
			model.addAttribute("firstName", user.getFirstName());
			String htmlFile=userService.readHtmlContent("templates/mailWeb.html");
			mailService.sendMail(user.getEmail(),"Student Registration",htmlFile);
			return "welcome";
		}
	}

}
