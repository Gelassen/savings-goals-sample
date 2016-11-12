package com.example.qapitalinterview.interactor;


public class GoalsInteractor {

    public String getGoalTitle(final int totalAmount) {
        final String template = "$%s";
        return String.format(template, totalAmount);
    }
}
