package com.financetracker.finance.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.financetracker.finance.models.TransactionModel;
import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.repository.UserModelRepository;
import com.financetracker.finance.services.TransactionService;
import com.financetracker.finance.validation.Transactionvalidator;

@RestController
@RequestMapping("/api/user/transaction")
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

@GetMapping
public List<TransactionModel> getAllTransactionsByUser(@RequestParam Long userId){
    return transactionService.getAllTransactionsByUser(userId);
}

@DeleteMapping
public ResponseEntity<Object> deleteTransaction(@RequestParam Long transactionId){
    transactionService.deleteTransaction(transactionId);
    return ResponseEntity.ok().body("Transaction deleted successfully");
}

@PutMapping("/update-receiver")
public ResponseEntity<Object> updatePersonReceived(@RequestParam Long transactionId, @RequestParam String personReceived){
    transactionService.updatePersonReceived(transactionId, personReceived);
    return ResponseEntity.ok().body("receiver updated successfully");
}
@PutMapping("/update-amount")
public ResponseEntity<Object> updateTransactionAmount(@RequestParam Long transactionId,@RequestParam float amount){
    if(amount<=0){
        return ResponseEntity.badRequest().body("Amount cannot be zero or negative");
    }
    else{
        transactionService.updateAmountTransacted(transactionId, amount);
        return ResponseEntity.ok().body("transaction amount updated successfully");
}
    }
}
    