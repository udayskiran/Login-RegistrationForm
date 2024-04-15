package com.example.demo.service;

public interface OtpGenerationService {

	public String generateOtp(String mail);
	public boolean verifyOtp(String email, String enteredOtp);
	
}
