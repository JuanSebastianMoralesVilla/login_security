package com.loginsecurity.login_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EntityScan("com.loginsecurity.login_security.model")
public class LoginSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginSecurityApplication.class, args);
		System.out.println("Proyecto final de seguridad");
	}

}
