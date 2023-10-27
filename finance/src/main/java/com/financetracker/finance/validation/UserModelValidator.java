package com.financetracker.finance.validation;

import com.financetracker.finance.interfaces.FieldValidator;
import com.financetracker.finance.models.UserModel;

public class UserModelValidator implements FieldValidator<UserModel> {
    
    @Override
    public String validateField(UserModel user){
        int maximumPasswordLength = 8;
        
        if(user.getPassword().isBlank() || user.getPassword().length()<maximumPasswordLength ){
            return "Password length must be at  least 8 characters long";
        }
        if(user.getFirstname().isBlank() || user.getLastname().isBlank()){
            return "firstname or lastname cannot be empty";
        }
        return "Success";

    }
    
}
