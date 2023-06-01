package com.loginsecurity.login_security.controller.impl;

import java.util.List;
import java.util.Optional;

import com.loginsecurity.login_security.model.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginsecurity.login_security.services.inter.UserService;

@Controller
@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

	@Autowired
	UserService userService;

	@PostMapping("/addusers")
	public ResponseEntity<?> addUser(@RequestBody UserApp userApp) {
		try {
			UserApp addedUserApp = userService.addUser(userApp);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedUserApp);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest()
					.body("Error en el sistema o compruebe el nombre de usuario ya que puede estar en uso.");
		}
	}

	@GetMapping("/users")
	public List<UserApp> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<UserApp> getUserByUsername(@PathVariable String username) {
		Optional<UserApp> userApp = userService.findByUsername(username);
		if (userApp.isPresent()) {
			return ResponseEntity.ok(userApp.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{userid}")
	public ResponseEntity<UserApp> getUserById(@PathVariable Long userid) {
	Optional<UserApp> userApp = userService.findById(userid);
		if (userApp.isPresent()) {
			return ResponseEntity.ok(userApp.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/deleteuser/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
		try {
			userService.deleteUser(userId);
			return ResponseEntity.ok("Usuario eliminado con éxito.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}

	}

	@PutMapping("/users/{userId}/reset-password")
	public ResponseEntity<String> resetUserPassword(@PathVariable Long userId) {
		try {
			userService.resetUserPassword(userId);
			return ResponseEntity.ok("Contraseña de usuario restablecida con éxito.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

}
