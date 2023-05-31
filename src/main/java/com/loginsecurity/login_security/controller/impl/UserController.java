package com.loginsecurity.login_security.controller.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.loginsecurity.login_security.model.User;
import com.loginsecurity.login_security.services.impl.UserServiceImp;
import com.loginsecurity.login_security.services.inter.UserService;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/{id}/lastlogin")
	public String getLastLogin(@PathVariable("id") long id) {
			Optional<User> user = userService.findById(id);
			if (!user.isEmpty()) {
				if (userService.getLastLogin(user.get().getId()) != null){
						String lastLogin = userService.getLastLogin(id);
						return lastLogin;
				}else{
					return "";
				}
			}
		return "El usuario no existe";
	}

	@PutMapping("/{userId}/change-password")
	public ResponseEntity<String> changePassword(
			@PathVariable Long userId,
			@RequestBody User user) {
		try {
			userService.changePassword(userId, user);
			return ResponseEntity.ok("Contraseña cambiada con éxito." + " Nueva contraseña (Sin hash):" + user.getPassword());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body("Error al cambiar la contraseña: " + e.getMessage());
		}
	}


	
}
