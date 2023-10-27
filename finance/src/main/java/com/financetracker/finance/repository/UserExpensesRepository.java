package com.financetracker.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.CrudRepsoitory;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.financetracker.finance.models.UserExpenses;
import com.financetracker.finance.models.UserModel;

@Repository
public interface UserExpensesRepository extends JpaRepository<UserExpenses,Long> {

    List<UserExpenses> findByUserModel(UserModel userModel);
}