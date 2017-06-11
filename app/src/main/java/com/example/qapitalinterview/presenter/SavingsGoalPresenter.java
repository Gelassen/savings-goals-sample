package com.example.qapitalinterview.presenter;

import android.content.Context;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.AppApplication;
import com.example.qapitalinterview.di.AppComponent;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.view.GoalDetailsActivity;
import com.example.qapitalinterview.view.IGoalView;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class SavingsGoalPresenter implements IGoalsPresenter {

    @Inject
    IModel model;

    private IGoalView view;
    private Subscription subscription = Subscriptions.empty();

    public SavingsGoalPresenter(Context context, IGoalView view) {
        ((AppComponent) AppApplication.getAppComponent()).inject(this);
        this.view = view;
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

        model.getSavingGoals().subscribe(new Observer<SavingsGoals>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError: ", e);
                view.showError();
            }

            @Override
            public void onNext(SavingsGoals savingsGoals) {
                Log.d(App.TAG, "Show the next item");
                if (savingsGoals != null && !savingsGoals.getSavingsGoals().isEmpty()) {
                    view.showData(savingsGoals.getSavingsGoals());
                } else {
                    view.showError();
                }
                Log.d(App.TAG, "savings goal receive result: " + (savingsGoals == null ? "null" : savingsGoals.getSavingsGoals().size()));
            }
        });
    }
}
