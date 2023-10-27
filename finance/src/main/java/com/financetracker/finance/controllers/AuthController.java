package com.financetracker.finance.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financetracker.finance.models.LoginData;
import com.financetracker.finance.services.AuthService;

@RestController
@RequestMapping("/user/login")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginData user){
        System.out.println(user.getPassword());
        Object res = authService.login(user.getEmail(),user.getPassword());

        if(res==null){
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return ResponseEntity.ok().body(res);
    }
    
}
 