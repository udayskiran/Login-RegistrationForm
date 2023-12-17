package com.example.demo.service;

import com.example.demo.model.User;
public interface UserService {
	
	public User saveData(User user);
	public boolean isEmailUnique(String Email);
}
