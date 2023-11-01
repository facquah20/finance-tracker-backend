package com.financetracker.finance.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.financetracker.finance.models.LoginData;
import com.financetracker.finance.models.UserDto;
import com.financetracker.finance.services.AuthService;

@RestController
@RequestMapping("/api/user/login")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody LoginData user){
        UserDto res = authService.login(user.getEmail(),user.getPassword());
        try{
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(res);
        if(res==null){
            return ResponseEntity.status(401).body("Unauthorized");
        }
        else{
            return new ResponseEntity<Object>(json,HttpStatus.ACCEPTED);
        }
    
        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
            
        }

        
    
    }
}
 