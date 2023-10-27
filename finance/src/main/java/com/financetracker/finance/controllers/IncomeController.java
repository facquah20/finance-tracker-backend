package com.financetracker.finance.controllers;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financetracker.finance.models.IncomeModel;
import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.repository.UserModelRepository;
import com.financetracker.finance.services.IncomeService;
import com.financetracker.finance.validation.IncomeValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/income")
public class IncomeController {
    @Autowired
    private IncomeService incomeService;

    @Autowired
    private UserModelRepository userModelRepository;

    @GetMapping
    public ResponseEntity<Object> getAllUserIncome(@RequestParam Long userId){
        ObjectMapper objectMapper = new ObjectMapper();
        List<IncomeModel> incomes = incomeService.getAllIncomeByUser(userId);
        try{
            String json = objectMapper.writeValueAsString(incomes);
            return ResponseEntity.ok().body(json);

        }catch(Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());

        }
        

    }
    
    @DeleteMapping
    public ResponseEntity<Object> deleteAnIncome(@RequestParam Long incomeId){
        incomeService.deleteIncomeById(incomeId);
        return ResponseEntity.ok().body("Income deleted");

    }

    @PutMapping("/update-amount")
    public ResponseEntity<Object> updateIncomeAmount(@RequestParam Long incomeId,@RequestParam float amount){
        incomeService.updateIncomeAmount(incomeId, amount);
        return ResponseEntity.ok().body("Amount updated Successfully");
    }

    @PutMapping("/update-source") 
    public ResponseEntity<Object> updateIncomeSource(@RequestParam Long incomeId, @RequestParam String source){
        boolean isValidString = source.isBlank() || source.length()<5;
        if(!isValidString){
            incomeService.updateIncomeSource(incomeId, source);
            return ResponseEntity.ok().body("Income source update successfully");

        }
        return ResponseEntity.badRequest().body("source field length must be at least 5 characters long");

    }

    @PostMapping
    public ResponseEntity<Object> addIncome(
        @RequestParam Long userId, 
        @RequestParam float amount,
        @RequestParam String source
    ){
         UserModel user = userModelRepository.findById(userId).orElse(null);
        //validate income fields
        IncomeValidator validator = new IncomeValidator();
        IncomeModel incomeModel = new IncomeModel();
        incomeModel.setAmount(amount);
        incomeModel.setSource(source);
         String res = validator.validateField(incomeModel);
         if(res.equalsIgnoreCase("success")){
            if(user == null)return ResponseEntity.badRequest().body("User with the given id does not exist");
            incomeModel.setUserModel(user);
            incomeService.addNewIncome(incomeModel);
            return ResponseEntity.ok().body("New Income added successfully");
         }
         else{
            return ResponseEntity.badRequest().body(res);
         }


    }
}