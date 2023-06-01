package com.loginsecurity.login_security.controller.impl;


import com.loginsecurity.login_security.model.UserApp;
import com.loginsecurity.login_security.services.inter.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Controller
@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class GeneralController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public ResponseEntity<UserApp> login(@RequestBody UserApp userP) {
        try {
            Optional<UserApp> user = userService.login(userP.getUsername(),userP.getPassword());
            return ResponseEntity.ok(user.get());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }catch (NullPointerException r){
            return ResponseEntity.status(403).build();
        }
    }
}
