package com.financetracker.finance.controllers;

import java.util.Date;
import java.util.List;

import com.financetracker.finance.models.UserExpenses;
import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.repository.UserModelRepository;
import com.financetracker.finance.services.UserExpensesService;
import com.financetracker.finance.validation.ExpensesValidator;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController  
@RequestMapping("/api/user/expenses")
public class ExpensesController {
    @Autowired
    private UserExpensesService userExpensesService;

    @Autowired
    private UserModelRepository userModelRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserExpenses(@PathVariable Long userId){
            ObjectMapper objectMapper = new ObjectMapper();
            List<UserExpenses> expenses = userExpensesService.getUserExpenses(userId);
        try{
            String json = objectMapper.writeValueAsString(expenses);
            return ResponseEntity.ok().body(json);

        }catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> addExpenses(
        @RequestParam Long userId, 
        @RequestParam float amount,
        @RequestParam String source,
        @RequestParam Date date
    ){
        UserModel user = userModelRepository.findById(userId).orElse(null);
        UserExpenses expense = new UserExpenses();
         //validating the fields here
            expense.setAmount(amount);
            expense.setDate(date);
            expense.setSource(source);
        ExpensesValidator validator = new ExpensesValidator();
        String res = validator.validateField(expense);

        if(res.equalsIgnoreCase("success")){
            if(user == null)return ResponseEntity.badRequest().body("User with the given id does not exist");
            expense.setUserModel(user);
            userExpensesService.createNewExpense(expense);
            return ResponseEntity.ok().body("Expense added");
             
        }
        else{
            return ResponseEntity.badRequest().body(res);
        }
        

    }

    @DeleteMapping
    public ResponseEntity<Object> deleteExpense(@RequestParam Long expenseId){
        userExpensesService.deleteExpense(expenseId);
        return ResponseEntity.ok().body("Deleted successfully");
    }

    @PutMapping("/update-amount")
    public ResponseEntity<Object> updateAmount(@RequestParam Long id,@RequestParam float amount ){
        userExpensesService.updateAmount(id, amount);
        return ResponseEntity.ok().body("Amount updated successfully");
    }

    @PutMapping("/update-source")
     public ResponseEntity<Object> updateSource(@RequestParam Long id,@RequestParam String source ){
        userExpensesService.updateSource(id, source);
        return ResponseEntity.ok().body("source updated successfully");
    }
    
}
 