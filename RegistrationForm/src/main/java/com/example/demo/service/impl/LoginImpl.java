package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Login;
import com.example.demo.repository.LoginRepo;
import com.example.demo.service.LoginService;
import com.example.demo.service.OtpGenerationService;

@Service
public class LoginImpl implements LoginService {

	@Autowired
	LoginRepo loginRepo;
	@Autowired
	OtpGenerationService otpService;
	
	@Override
	public Login loginDetails(Login loginfo) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();

		Optional<Login> user = Optional.ofNullable(loginRepo.findByEmailIgnoreCase(loginfo.getEmail()));
		if (user.isPresent()) {
			Login loginDb = user.get();
			if(byCrypt.matches(loginfo.getPassword(), loginDb.getPassword())) {
				return loginDb;
			}
		}
		return null;
	}
	
	@Override
	public String forgotPasswordSendMail(String email) {
		Optional<Login> userOp = Optional.ofNullable(loginRepo.findByEmailIgnoreCase(email));
		if (userOp.isPresent()) {
			otpService.generateOtp(email);
			return "otp";
		}
		return "invalid";
	}

	@Override
	public String forgetPasswordVerifyEmail(String email, String otp, String newPass, String confPass) {
		BCryptPasswordEncoder byCrypt = new BCryptPasswordEncoder();

		Optional<Login> userOp = Optional.ofNullable(loginRepo.findByEmailIgnoreCase(email));
		if (userOp.isPresent()) {
			if (otpService.verifyOtp(email, otp)) {
				if (newPass.equals(confPass)) {
					String encryptPassword = byCrypt.encode(confPass);
					userOp.get().setPassword(encryptPassword);
					loginRepo.save(userOp.get());
					return "changed";
				} else {
					return "notMatched";
				}
			} else {
				return "incorrect";
			}
		}
		return "invalid";

	}
}
