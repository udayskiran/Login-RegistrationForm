package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Login;
import com.example.demo.model.User;
import com.example.demo.repository.LoginRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepo userRepo;
	@Autowired
	LoginRepo loginRepo;
	
	@Override
	public User saveData(User user) {
		
		Login login=new Login();
		login.setEmail(user.getEmail());
		login.setPassword(user.getPassword());
		loginRepo.save(login);
		return userRepo.save(user);
		
	}

	@Override
	public boolean isEmailUnique(String Email) {
		
		User existingUser=userRepo.findByEmail(Email);
		return existingUser == null;
	}

}
