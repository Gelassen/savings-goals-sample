package com.example.qapitalinterview.model;


import android.databinding.BaseObservable;

public class BindedSavingsGoal  {
    public String goal;
    public String balance;

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
