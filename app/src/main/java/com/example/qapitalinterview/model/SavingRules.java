package com.example.qapitalinterview.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavingRules {

    @SerializedName("savingsRules")
    @Expose
    private List<SavingsRule> savingsRules = new ArrayList<SavingsRule>();

    /**
     *
     * @return
     * The savingsRules
     */
    public List<SavingsRule> getSavingsRules() {
        return savingsRules;
    }

    /**
     *
     * @param savingsRules
     * The savingsRules
     */
    public void setSavingsRules(List<SavingsRule> savingsRules) {
        this.savingsRules = savingsRules;
    }

}
