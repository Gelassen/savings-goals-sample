package com.example.qapitalinterview.integration.fake;


import android.content.Context;

import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.IGoalView;

public class TestGoalPresenter extends SavingsGoalPresenter {

    public TestGoalPresenter(Context context, IGoalView view) {
        super(context, view);
    }



}
