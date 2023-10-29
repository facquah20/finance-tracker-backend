package com.financetracker.finance.models;


import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Income")
public class IncomeModel {
    @Id
    @GeneratedValue
    private Long id;

    private String source;
    private float amount;

    @ManyToOne
    @JoinColumn(name = "income_user_id")
    private UserModel userModel;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @JsonBackReference(value = "user-income")
    public UserModel getUserModel() {
        return this.userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
   
    @Autowired
    public IncomeModel(Long id, String source, float amount, UserModel userModel) {
        this.id = id;
        this.source = source;
        this.amount = amount;
    }

    public IncomeModel(){

    }
    
    
}
