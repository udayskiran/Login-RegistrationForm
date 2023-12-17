package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Login;
import com.example.demo.repository.LoginRepo;
import com.example.demo.service.LoginService;
@Service
public class LoginImpl implements LoginService{

	@Autowired
	LoginRepo loginRepo;
	
	public Login loginDetails(Login loginfo) {
		try {
			if (loginfo.getEmail() != null && loginfo.getPassword() != null) {
				// If email and password are provided, try to find a login record by email and
				// password
				return loginRepo.findByEmailIgnoreCaseAndPassword(loginfo.getEmail(), loginfo.getPassword());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; // Return null if login details are not found
	}
}
