package com.example.qapitalinterview.presenter;


import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;

import java.util.List;

public interface IGoalDetailsPresenter {
    void onStop();
    void onUploadDetails(final int goalId);
    void onUpdateModel(SavingsGoal goal);
    void onUpdateModel(List<Feed> feeds);
    void onUpdateFilter(List<SavingsRule> rules);
    void onUploadData(int goalId);
}
