package com.loginsecurity.login_security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.loginsecurity.login_security.services.impl.UserServiceImp;
import com.loginsecurity.login_security.services.inter.UserService;

@SpringBootTest
class LoginSecurityApplicationTests {

	@Test
	void contextLoads() {
	}

	 @Test
	    public void testHashPassword() {
	        String originalPassword = "example_password";
	        String expectedHash = "e250P69CDLbDQrYghKmhjjQsgeAwh3AQWfcE1yl//lo=";

	        UserService userService = new UserServiceImp();

	        String generatedHash = userService.hashPassword(originalPassword);

	        assertEquals(expectedHash, generatedHash);
	    }
}
