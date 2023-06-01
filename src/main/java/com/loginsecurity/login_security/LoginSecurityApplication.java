package com.loginsecurity.login_security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.loginsecurity.login_security.model.UserApp;
import com.loginsecurity.login_security.model.UserType;
import com.loginsecurity.login_security.repository.UserRepository;
import com.loginsecurity.login_security.services.inter.UserService;


@SpringBootApplication
//@EntityScan("com.loginsecurity.login_security.model")
public class LoginSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginSecurityApplication.class, args);
		System.out.println("Proyecto final de seguridad");
	}
	
	
	@Bean
	public CommandLineRunner runner (UserRepository userRepository, UserService userService) {
		
    return (args) -> {
		
		// Usuarios de prueba
    	
		UserApp userAdmin = new UserApp();
		userAdmin.setUsername("admin");
		userAdmin.setPassword("12345");
    	userAdmin.setType("ADMIN");
    	userService.addUser(userAdmin);
  
		
    	
    	UserApp userOper = new UserApp();
		userOper.setUsername("oper");
		userOper.setPassword("12345");
    	userOper.setType("USER");
    	userService.addUser(userOper);
    	
    	UserApp user3 = new UserApp();
		user3.setUsername("example_user1");
		user3.setPassword("example_password");
    	user3.setType("USER");
    	userService.addUser(user3);
	};
	
	}

}
