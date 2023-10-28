package com.financetracker.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financetracker.finance.models.TransactionModel;
import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.repository.UserModelRepository;
import com.financetracker.finance.services.TransactionService;
import com.financetracker.finance.validation.Transactionvalidator;

@RestController
@RequestMapping("/user/transaction")
public class TransactionController {
    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private TransactionService transactionService;

    @PostMapping
public ResponseEntity<Object> addTransaction(
    @RequestParam Long userId,
    @RequestParam float amount,
    @RequestParam String personReceived,
    @RequestParam String description
){
    //valid necessary fields
    Transactionvalidator validator = new Transactionvalidator();
     String res = validator.validateField(description);
     /*Check if correct description was given and the personReceived field is not blank */
     if(res.equalsIgnoreCase("success") && !personReceived.isBlank()){
        UserModel user = userModelRepository.findById(userId).orElse(null);
        if(user!=null){
            //create the transaction object
            TransactionModel transaction = new TransactionModel();
            transaction.setAmount(amount);
            transaction.setUserModel(user);
            transaction.setPersonReceived(personReceived);
            transaction.setDescription(description);
            
            //save the new transaction
            transactionService.recordNewTransaction(transaction);
            return new ResponseEntity<Object>("Transaction added",HttpStatus.CREATED);
   
        }
     }
     return ResponseEntity.badRequest().body(res);

}

}
    