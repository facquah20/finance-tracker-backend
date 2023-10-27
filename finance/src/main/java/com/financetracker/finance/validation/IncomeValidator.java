package com.financetracker.finance.validation;
import com.financetracker.finance.interfaces.FieldValidator;
import com.financetracker.finance.models.IncomeModel;


public class IncomeValidator implements FieldValidator<IncomeModel> {
    @Override
    public String validateField(IncomeModel incomeModel){
        String source = incomeModel.getSource();
        float amount = incomeModel.getAmount();
        int maximumSourceChar = 100; // set the maximum character of the source to 100
        int leastSourceChar = 5;
        float leastAmount = 0;

        if(source.isBlank() || source.length()>maximumSourceChar || source.length()<leastSourceChar){
            return "least number of characters for income source is 5";
        }
        if(amount<=leastAmount){
            return "Amount cannot be zero or negative";
        }
        else return "success";
    }
    }
    

