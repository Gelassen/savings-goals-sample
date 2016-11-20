package com.example.qapitalinterview.view;


import android.content.Context;

import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;

import java.util.List;

public interface IGoalDetailsView {
    void showGoal(SavingsGoal goal);
    void showFilters(List<SavingsRule> rules);
    void showAchievements(List<Feed> feeds);
    void showWeeklyProgress(String formattedValue);
    Context getContext();
    void showError();
}
