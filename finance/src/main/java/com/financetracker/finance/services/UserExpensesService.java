package com.financetracker.finance.services;

import java.util.Collections;
import java.util.List;
//import java.util.ArrayList;
import com.financetracker.finance.models.UserExpenses;
import com.financetracker.finance.models.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.financetracker.finance.repository.UserExpensesRepository;
import com.financetracker.finance.repository.UserModelRepository;

@Service
public class UserExpensesService {
    @Autowired
    private UserExpensesRepository userExpensesRepository;
    
    @Autowired
    private UserModelRepository userModelRepository;

    /**
     * @params {Long} userId 
     * @description retuns all the expenses of a user with a given id
     * 
     */
        public List<UserExpenses> getUserExpenses(Long id){
            UserModel user = userModelRepository.findById(id).orElse(null);
            if(user == null){
                return Collections.emptyList();
            }
            
           else {
             return userExpensesRepository.findByUserModel(user);
           }
        }

        public void createNewExpense(UserExpenses expenses){
            userExpensesRepository.save(expenses);
        }

        public void deleteExpense(Long id){
            userExpensesRepository.deleteById(id);
        }
        @Transactional
        public void updateAmount(Long id, float amount){
            userExpensesRepository.findById(id)
            .ifPresent(expense->{
                expense.setAmount(amount);
                userExpensesRepository.save(expense);
            });
        }

        @Transactional
        public void updateSource(Long id, String source){
            userExpensesRepository.findById(id)
            .ifPresent(expense->{
                expense.setSource(source);
                userExpensesRepository.save(expense);
            });
        }
}
