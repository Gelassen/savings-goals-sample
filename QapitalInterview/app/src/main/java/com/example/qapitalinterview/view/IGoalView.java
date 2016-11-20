package com.example.qapitalinterview.view;

import android.content.Context;

import com.example.qapitalinterview.model.SavingsGoal;

import java.util.List;

/**
 * Created by John on 10/30/2016.
 */

public interface IGoalView {
    void showData(List<SavingsGoal> data);
    void showError();
    Context getContext();
}
