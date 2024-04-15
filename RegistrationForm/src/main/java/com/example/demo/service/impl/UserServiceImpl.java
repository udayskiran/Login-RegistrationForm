package com.example.demo.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.example.demo.entity.Login;
import com.example.demo.entity.User;
import com.example.demo.repository.LoginRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	@Autowired
	LoginRepo loginRepo;

	@Override
	public User saveData(User user) {
		if(userRepo.findByEmail(user.getEmail())==null) {
			BCryptPasswordEncoder byCrypt=new BCryptPasswordEncoder();
			String encryptPassword =byCrypt.encode(user.getPassword());
			user.setPassword(encryptPassword);
			Login login = new Login();
			login.setEmail(user.getEmail());
			login.setPassword(user.getPassword());
			login.setName(user.getFirstName());
			loginRepo.save(login);
			return userRepo.save(user);
		}
		return null;

	}
	public String readHtmlContent(String filePath) {
        try {
            Resource resource = new ClassPathResource(filePath);
            byte[] contentBytes = FileCopyUtils.copyToByteArray(resource.getInputStream());
            return new String(contentBytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Handle exception
            return "";
        }
    }

}
