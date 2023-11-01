package com.financetracker.finance.controllers;

import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** class that handles the url that display views for the user */
@Controller
public class ViewController{

    @GetMapping("/")
    public String displayFirstPage(){
        return "index";
    }
    @GetMapping("/login")
    public String displayAuthPage(){
        return "auth";
    }

    @GetMapping("/income")
    public String displayIncomePage(){
        return "income";
    }
    @GetMapping("/transactions")
    public String displayTransactionsPage(){
        return "transactions";
    }

    @GetMapping("/expenses")
    public String displayExpensesPage(){
        return "expenses";
    }
}