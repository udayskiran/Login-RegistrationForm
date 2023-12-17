package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Login;

public interface LoginRepo extends JpaRepository<Login, Long>{

	Login findByEmailIgnoreCaseAndPassword(String email, String password);

}
