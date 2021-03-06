package com.example.qapitalinterview.presenter;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.AppApplication;
import com.example.qapitalinterview.di.IComponent;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.view.GoalDetailsActivity;
import com.example.qapitalinterview.view.IGoalView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class SavingsGoalPresenter implements IGoalsPresenter {

    @Inject
    IModel model;

    private IGoalView view;
    private Subscription subscription = Subscriptions.empty();
    private boolean isThereWorkInBackground;

    public SavingsGoalPresenter(Context context, IGoalView view) {
        ((IComponent) AppApplication.getAppComponent()).inject(this);
        this.view = view;
        this.isThereWorkInBackground = false;
    }

    public IModel getModel() {
        return model;
    }

    public IGoalView getView() {
        return view;
    }

    @Override
    public void onItemClick(int goalId) {
        GoalDetailsActivity.start(view.getContext(), goalId);
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onRefreshData() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        setWorkInBackground(true);
        view.showInProgress(true);

        Observable<SavingsGoals> observable = model.getSavingGoals();
        observable.subscribe(new Observer<SavingsGoals>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError: ", e);
                setWorkInBackground(false);
                view.showError();
                view.showData(new ArrayList<SavingsGoal>());
            }

            @Override
            public void onNext(SavingsGoals savingsGoals) {
                Log.d(App.TAG, "Show the next item");
                setWorkInBackground(false);
                if (savingsGoals != null && !savingsGoals.getSavingsGoals().isEmpty()) {
                    view.showData(savingsGoals.getSavingsGoals());
                    view.showInProgress(false);
                    view.showEmptyScreen(false);
                } else {
                    view.showEmptyScreen(false);
                }
                Log.d(App.TAG, "savings goal receive result: " + (savingsGoals == null ? "null" : savingsGoals.getSavingsGoals().size()));
            }
        });
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public boolean isWorkInBackground() {
        return isThereWorkInBackground;
    }

    private void setWorkInBackground(boolean isThereWorkInBackground) {
        this.isThereWorkInBackground = isThereWorkInBackground;
    }
}
