package com.financetracker.finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financetracker.finance.models.IncomeModel;
import com.financetracker.finance.models.UserModel;

public interface IncomeRepository extends JpaRepository<IncomeModel,Long>{
    List<IncomeModel> findByUserModel(UserModel userModel);
}