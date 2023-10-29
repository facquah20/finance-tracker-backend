package com.financetracker.finance.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue
    private Long id;
    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;
     private String password;
    
     @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<UserExpenses> expenses = new ArrayList<>();

     @OneToMany(mappedBy = "userModel", cascade = CascadeType.ALL, orphanRemoval = true )
     private List<IncomeModel> income = new ArrayList<>();

     @OneToMany(mappedBy = "userModel",cascade = CascadeType.ALL , orphanRemoval = true)
     private List<TransactionModel> transactions = new ArrayList<>();

    public UserModel() {
    }

    public UserModel(Long id, String firstname, String lastname, String password, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
    @JsonBackReference(value="user-expenses")
    public List<UserExpenses> getExpenses() {
        return this.expenses;
    }

    public void setExpenses(List<UserExpenses> expenses) {
        this.expenses = expenses;
    }

    @JsonBackReference(value = "user-income")
    public List<IncomeModel> getIncome() {
        return this.income;
    }

    public void setIncome(List<IncomeModel> income) {
        this.income = income;
    }

     @JsonBackReference(value = "user-transactions")
     public List<TransactionModel> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<TransactionModel> transactions) {
        this.transactions = transactions;
    }

}
