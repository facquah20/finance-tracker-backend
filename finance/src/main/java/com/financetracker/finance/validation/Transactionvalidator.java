package com.financetracker.finance.validation;

import com.financetracker.finance.interfaces.FieldValidator;

public class Transactionvalidator  implements FieldValidator<String>{

    @Override
    public String validateField(String description){
        int maximumDescriptionChar = 255;
        int minimumDescriptionChar = 10;
         if(description.length()<minimumDescriptionChar || description.length()>maximumDescriptionChar){
            return "Minimum characters for description is 10 and maximum is 255";
         }
         return "success";
    }
    
}
