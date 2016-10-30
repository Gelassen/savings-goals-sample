package com.example.qapitalinterview.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingsGoals {

    @SerializedName("savingsGoals")
    @Expose
    private List<SavingsGoal> savingsGoals = new ArrayList<SavingsGoal>();

    /**
     *
     * @return
     * The savingsGoals
     */
    public List<SavingsGoal> getSavingsGoals() {
        return savingsGoals;
    }

    /**
     *
     * @param savingsGoals
     * The savingsGoals
     */
    public void setSavingsGoals(List<SavingsGoal> savingsGoals) {
        this.savingsGoals = savingsGoals;
    }

}
