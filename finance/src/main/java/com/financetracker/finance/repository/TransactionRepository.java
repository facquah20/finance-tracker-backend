package com.financetracker.finance.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import com.financetracker.finance.models.TransactionModel;
import com.financetracker.finance.models.UserModel;

public interface TransactionRepository extends JpaRepository<TransactionModel,Long> {
    List<TransactionModel> findByUserModel(UserModel userModel);
    
}
