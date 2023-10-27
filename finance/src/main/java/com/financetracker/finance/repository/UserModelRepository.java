package com.financetracker.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.financetracker.finance.models.UserModel;

@Repository
public interface UserModelRepository extends JpaRepository<UserModel, Long>{
    UserModel findByEmail(String email);
    
} 