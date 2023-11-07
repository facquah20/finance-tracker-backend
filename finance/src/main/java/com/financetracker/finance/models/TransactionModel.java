package com.financetracker.finance.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String personReceived;
    private String description;
    private float amount;

    @ManyToOne
    @JoinColumn(name = "transaction_user_id")
    private UserModel userModel;

    public TransactionModel(){
        
    }

    public TransactionModel(Long id, String personReceived, String description, float amount) {
        this.id = id;
        this.personReceived = personReceived;
        this.description = description;
        this.amount = amount;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonReceived() {
        return this.personReceived;
    }

    public void setPersonReceived(String personReceived) {
        this.personReceived = personReceived;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return this.amount;
    }
    public void setAmount(float amount) {
        this.amount = amount;
    }

    @JsonBackReference(value = "user-transactions")
    public UserModel getUserModel() {
        return this.userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

}
