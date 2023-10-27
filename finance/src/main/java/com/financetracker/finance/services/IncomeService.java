package com.financetracker.finance.services;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financetracker.finance.models.UserModel;
import com.financetracker.finance.models.IncomeModel;
import com.financetracker.finance.repository.IncomeRepository;
import com.financetracker.finance.repository.UserModelRepository;

import jakarta.transaction.Transactional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserModelRepository userModelRepository;

    @Autowired
    public IncomeService(IncomeRepository incomeRepository, UserModelRepository userModelRepository){
        this.incomeRepository = incomeRepository;
        this.userModelRepository = userModelRepository;
    }

    public List<IncomeModel> getAllIncomeByUser(Long userId){
        UserModel user = userModelRepository.findById(userId).orElse(null);
        if(user == null){
            return Collections.emptyList();
        }
        else{
            return incomeRepository.findByUserModel(user);
        }
    }
    
    public void deleteIncomeById(Long incomeId){
        incomeRepository.deleteById(incomeId);
    }
    public void addNewIncome(IncomeModel incomeModel){
        incomeRepository.save(incomeModel);
    }
    @Transactional
    public void updateIncomeAmount(Long incomeId, float amount){
        incomeRepository.findById(incomeId)
        .ifPresent(income->{
            income.setAmount(amount);
        });
    }

    @Transactional
    public void updateIncomeSource(Long incomeId, String source){
        incomeRepository.findById(incomeId)
        .ifPresent(income->{
            income.setSource(source);
        });
    }
}
