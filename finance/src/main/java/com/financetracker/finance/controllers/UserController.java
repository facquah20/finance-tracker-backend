package com.financetracker.finance.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.financetracker.finance.models.UserDto;
import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.services.UserService;
import com.financetracker.finance.validation.UserModelValidator;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserModelValidator userModelValidator;

    @PostMapping("/register")
    public ResponseEntity<Object> createUserAccount(@RequestBody UserModel userModel){
        try{
            /* validate the fields */
            String state = userModelValidator.validateField(userModel);
            if(!state.equalsIgnoreCase("success")){
                return new ResponseEntity<Object>(state,HttpStatus.valueOf(400));
            }
            else{
                UserModel user = userService.createUserAccount(userModel);
                if(user == null){
                    return new ResponseEntity<Object>("Email Already Exist", HttpStatus.valueOf(400));
                }
                return new ResponseEntity<Object>("Account created successfully",HttpStatus.CREATED);
            }
            

            

        }catch(Exception e){
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.valueOf(500));
        }
    }
    
    @GetMapping
    public List<UserModel> getAllUsers(){
        return userService.getAllUsers(); // return all users
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable Long id){
        UserDto user = userService.findeUserById(id);
       if(user==null){
        return new ResponseEntity<Object>("User not found",HttpStatus.valueOf(404));
       }
       return new ResponseEntity<Object>(user,HttpStatus.OK);
    }
    
}
 