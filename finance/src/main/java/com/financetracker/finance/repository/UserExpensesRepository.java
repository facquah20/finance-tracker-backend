package com.financetracker.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.financetracker.finance.models.UserExpenses;
//import com.financetracker.finance.models.UserModel;

@Repository
public interface UserExpensesRepository extends JpaRepository<UserExpenses,Long> {
    @Query(value = "SELECT * FROM Expenses  u WHERE u.user_id =:id ",nativeQuery = true)
    List<UserExpenses> findAllByUserModel(@Param("id")Long id);
}