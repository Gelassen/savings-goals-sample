package com.example.qapitalinterview.presenter;


import android.os.Bundle;
import android.support.v4.app.LoaderManager;

import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;

import java.util.List;

public interface IGoalDetailsPresenter {
    void onStop();
    void onUploadDetails(final int goalId);
    void onUploadData(int goalId);
    void onCheckCachedData(LoaderManager manager, Bundle args);
    void onApplyFilter(LoaderManager manager, Bundle args);
}
