package com.example.demo.service;

import com.example.demo.entity.Login;

public interface LoginService {
	public Login loginDetails(Login logininfo);
	public String forgotPasswordSendMail(String email);
	public String forgetPasswordVerifyEmail(String email, String otp, String newPass, String confPass);
}
