package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.demo.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
@Service
public class EmailServiceImpl implements EmailService {
	@Autowired
	private JavaMailSender mailsender;
    @Override
    public void sendMail(String toMail, String subject, String body) {

		MimeMessage message = mailsender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
        	helper.setTo(toMail);
    		helper.setSubject(subject);
    		helper.setText(body,true);
    		mailsender.send(message);
        }catch (MessagingException e) {
            // Handle exception
        }
		
	}

}
