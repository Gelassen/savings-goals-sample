package com.example.qapitalinterview.presenter;

import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.view.GoalDetailsActivity;
import com.example.qapitalinterview.view.IGoalView;

import rx.Observer;
import rx.Subscription;
import rx.schedulers.Timestamped;
import rx.subscriptions.Subscriptions;

/**
 * Created by John on 10/30/2016.
 */

public class SavingsGoalPresenter implements IGoalsPresenter {

    private IModel model;
    private IGoalView view;
    private Subscription subscription = Subscriptions.empty();

    public SavingsGoalPresenter(IGoalView view) {
        this.view = view;
        this.model = new Model(view.getContext());
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

        model.getSavingGoals().subscribe(new Observer<SavingsGoals>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError: ", e);
            }

            @Override
            public void onNext(SavingsGoals savingsGoals) {
                Log.d(App.TAG, "savings goal receive result: " + savingsGoals.getSavingsGoals().size());
                Log.d(App.TAG, "Show the next item");
                if (savingsGoals != null && !savingsGoals.getSavingsGoals().isEmpty()) {
                    view.showData(savingsGoals.getSavingsGoals());
                } else {
                    view.showError();
                }
            }
        });
    }
}
