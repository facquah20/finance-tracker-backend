package com.financetracker.finance.services;

import java.util.List;

import com.financetracker.finance.models.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetracker.finance.repository.UserModelRepository;
import com.financetracker.finance.models.UserModel;

@Service
public class UserService {
    
    @Autowired
    private UserModelRepository userModelRepository;

    public UserModel createUserAccount(UserModel userModel){
        //check if user with the given email exist
        UserModel user = userModelRepository.findByEmail(userModel.getEmail());
        if(user == null){
            //save the account into the database since the given email doesn't exist
            return userModelRepository.save(userModel);
        }
        else{
            return null;
        }
    }

    public List<UserModel> getAllUsers(){
        return userModelRepository.findAll();
    }
    
    public UserDto findeUserById(Long id){
        UserModel user = userModelRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        return new UserDto(
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            id
        );
    }
    
} 