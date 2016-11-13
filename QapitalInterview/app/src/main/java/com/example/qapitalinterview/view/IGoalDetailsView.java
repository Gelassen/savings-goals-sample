package com.example.qapitalinterview.view;


import android.content.Context;

import com.example.qapitalinterview.model.SavingsGoal;

public interface IGoalDetailsView {
    void showData();
    void showGoal(SavingsGoal goal);
    Context getContext();
    void showError();
}
