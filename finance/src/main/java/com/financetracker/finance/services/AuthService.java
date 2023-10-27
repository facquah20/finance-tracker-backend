package com.financetracker.finance.services;

import com.financetracker.finance.repository.UserModelRepository;
import com.financetracker.finance.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserModelRepository userModelRepository;
    
    @Autowired
    public AuthService(UserModelRepository userModelRepository){
        this.userModelRepository = userModelRepository;
    }

    public Object login(String email,String password){
        UserModel user = userModelRepository.findByEmail(email);
        if(user!=null && password.equals(user.getPassword())){
            return "Login successfull" ;   
        }
        return null;
    }
    
}
