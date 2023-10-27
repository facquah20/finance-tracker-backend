package com.financetracker.finance.services;

import java.util.Collections;
import java.util.List;
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
    private  UserModelRepository userModelRepository;

    @Autowired
    public UserExpensesService(
        UserExpensesRepository userExpensesRepository,
        UserModelRepository userModelRepository
        ){
            this.userExpensesRepository = userExpensesRepository;
            this.userModelRepository = userModelRepository;
        }

        public List<UserExpenses> getUserExpenses(Long userId){
            //retrieve the user from the database
            UserModel user = userModelRepository.findById(userId).orElse(null);
            if(user == null){
                return Collections.emptyList();
            }
            return userExpensesRepository.findAllByUserModel(userId);
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
