package com.loginsecurity.login_security.controller.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.loginsecurity.login_security.model.User;
import com.loginsecurity.login_security.services.impl.UserServiceImp;
import com.loginsecurity.login_security.services.inter.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/lastlogin")
	public String getLastLogin(User lastloginuser) {
	    String username = lastloginuser.getUsername();
	    User user = userService.findByUsername(username);

	    if (user != null) {
	        LocalDateTime lastLogin = user.getLastLogin();
	        if (lastLogin != null) {
	            return "Último inicio de sesión: " + lastLogin.toString();
	        } else {
	            return "No se ha registrado ningún inicio de sesión previo.";
	        }
	    } else {
	        return "Usuario no encontrado.";
	    }
	}
	
	  @PutMapping("/{userId}/change-password")
	    public ResponseEntity<String> changePassword(
	            @PathVariable Long userId,
	            @RequestBody User user) {
	        try {
	            userService.changePassword(userId, user);
	            return ResponseEntity.ok("Contraseña cambiada con éxito."+ " Nueva contraseña (Sin hash):" + user.getPassword());
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body("Error al cambiar la contraseña: " + e.getMessage());
	        }
	    }

	
	
}
