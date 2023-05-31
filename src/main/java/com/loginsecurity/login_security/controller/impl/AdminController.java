package com.loginsecurity.login_security.controller.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loginsecurity.login_security.model.User;

import com.loginsecurity.login_security.services.inter.UserService;

import javassist.NotFoundException;

@Controller
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@PostMapping("/addusers")
	public ResponseEntity<?> addUser(@RequestBody User user) {
		try {
			User addedUser = userService.addUser(user);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest()
					.body("Error en el sistema o compruebe el nombre de usuario ya que puede estar en uso.");
		}
	}

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/users/{username}")
	public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
		User user = userService.findByUsername(username);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{userid}")
	public ResponseEntity<User> getUserById(@PathVariable Long userid) {
		User user = userService.findById(userid).get();
		if (user != null) {
			return ResponseEntity.ok(user);
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