package com.loginsecurity.login_security.controller.impl;

import java.util.Optional;

import com.loginsecurity.login_security.model.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.loginsecurity.login_security.services.inter.UserService;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;


	@PutMapping("/{userId}/change-password")
	public ResponseEntity<String> changePassword(
			@PathVariable Long userId,
			@RequestBody UserApp userApp) {
		try {
			userService.changePassword(userId, userApp);
			return ResponseEntity.ok("Contraseña cambiada con éxito." + " Nueva contraseña (Sin hash):" + userApp.getPassword());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Error al cambiar la contraseña: " + e.getMessage());
		}
	}


	
}
