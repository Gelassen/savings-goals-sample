package com.example.qapitalinterview.entity;


import com.example.qapitalinterview.model.SavingsRule;

public class FilterManager {

    private static final int FILTER_ALL_ITEMS_ID = -1;
    private static final String FILTER_ALL_ITEMS = "all";

    public SavingsRule getAllItemsFilter() {
        SavingsRule result = new SavingsRule();
        result.setType(FILTER_ALL_ITEMS);
        result.setId(FILTER_ALL_ITEMS_ID);
        return result;
    }

    public boolean isFilterApplied(SavingsRule savingsRule) {
        return FILTER_ALL_ITEMS_ID != savingsRule.getId();
    }
}
