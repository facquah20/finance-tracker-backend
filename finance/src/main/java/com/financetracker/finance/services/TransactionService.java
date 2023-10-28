package com.financetracker.finance.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.financetracker.finance.models.TransactionModel;
import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.repository.TransactionRepository;
import com.financetracker.finance.repository.UserModelRepository;

public class TransactionService {
    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
    public void recordNewTransaction(TransactionModel transactionModel){

        transactionRepository.save(transactionModel);
    }

    public List<TransactionModel> getAllTransactionsByUser(Long userId){
        UserModel user = userModelRepository.findById(userId).orElse(null);
        if(user == null){
            return Collections.emptyList();
        }
        return transactionRepository.findByUserModel(user);
    }

    public void deleteTransaction(Long transactionId){
        transactionRepository.deleteById(transactionId);
    }

    public void updatePersonReceived(Long transactionId,String personReceived){
        transactionRepository.findById(transactionId)
        .ifPresent(transaction->{
            transaction.setPersonReceived(personReceived);
            transactionRepository.save(transaction);
        });
    }
    
    public void updateAmountTransacted(Long transactionId, float amount){
        transactionRepository.findById(transactionId)
        .ifPresent(transaction->{
            transaction.setAmount(amount);
            transactionRepository.save(transaction);
        });

    }

    
}
