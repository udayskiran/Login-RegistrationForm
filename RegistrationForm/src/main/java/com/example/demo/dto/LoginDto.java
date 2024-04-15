package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
	private String email;
	private String otp;
	private String newPassword;
	private String confPassword;
}
