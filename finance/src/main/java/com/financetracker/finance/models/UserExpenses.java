package com.financetracker.finance.models;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

//import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="Expenses")
public class UserExpenses {
    @Id
    @GeneratedValue
    private Long id;
    private float amount;
    private String source;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "expenses_user_id")
    private UserModel userModel;

    public UserExpenses(){
        
    }

     public UserExpenses(Long id, float amount, String source, Date date) {
        this.id = id;
        this.amount = amount;
        this.source = source;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }
    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @JsonBackReference(value = "user-expenses")
    public UserModel getUserModel() {
        return this.userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

}
