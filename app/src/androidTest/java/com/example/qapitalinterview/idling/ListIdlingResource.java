package com.example.qapitalinterview.idling;


import android.support.test.espresso.IdlingResource;

import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.MainActivity;

public class ListIdlingResource implements IdlingResource {

    private ResourceCallback resourceCallback;

    private MainActivity activity;

    public ListIdlingResource(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public String getName() {
        return ListIdlingResource.class.getName();
    }

    @Override
    public boolean isIdleNow() {
        SavingsGoalPresenter presenter = (SavingsGoalPresenter) activity.getPresenter();
        if (presenter.isWorkInBackground()) {
            return false;
        } else {
            resourceCallback.onTransitionToIdle();
            return true;
        }
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
