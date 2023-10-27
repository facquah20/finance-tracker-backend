package com.financetracker.finance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.financetracker.finance.validation.UserModelValidator;

@Configuration
public class UserModelValidatorConfig {

    @Bean
    public UserModelValidator validateUserModel(){
        return new UserModelValidator();
    }
    
}
