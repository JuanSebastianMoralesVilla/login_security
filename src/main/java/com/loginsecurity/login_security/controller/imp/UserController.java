package com.loginsecurity.login_security.controller.imp;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.loginsecurity.login_security.model.User;
import com.loginsecurity.login_security.services.imp.UserServiceImp;
import com.loginsecurity.login_security.services.interfaces.UserService;

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
	
	@PutMapping("/change-password")
	public ResponseEntity<String> changePassword(User userp, @RequestParam String newPassword) {
	    String username = userp.getUsername();
	    User user = userService.findByUsername(username);

	    try {
	        userService.changePassword(user.getId(), newPassword);
	        return ResponseEntity.ok("Contraseña cambiada con éxito.");
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body("Error al cambiar la contraseña: " + e.getMessage());
	    }
	}

}
