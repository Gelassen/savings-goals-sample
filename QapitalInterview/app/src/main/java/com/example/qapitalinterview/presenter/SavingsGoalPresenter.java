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
import rx.subscriptions.Subscriptions;

/**
 * Created by John on 10/30/2016.
 */

public class SavingsGoalPresenter implements GoalsPresenter {

    private IModel model;
    private IGoalView view;
    private Subscription subscription = Subscriptions.empty();

    public SavingsGoalPresenter(IGoalView view) {
        this.view = view;
        this.model = new Model(view.getContext());
    }

    @Override
    public void onItemClick() {
        GoalDetailsActivity.start(view.getContext());
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

        model.cacheSavingGoals().subscribe(new Observer<SavingsGoals>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "onError", e);
            }

            @Override
            public void onNext(SavingsGoals savingsGoals) {
                view.showData();
                Log.d(App.TAG, "onNext: " + savingsGoals.getSavingsGoals().size());
            }
        });

//        model.getSavingGoals().subscribe(new Observer<SavingsGoals>() {
//            @Override
//            public void onCompleted() {
//                // no op
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(App.TAG, "Failed tp get saving goals", e);
//                view.showError();
//            }
//
//            @Override
//            public void onNext(SavingsGoals savingsGoals) {
//                if (savingsGoals != null && !savingsGoals.getSavingsGoals().isEmpty()) {
//                    view.showData(savingsGoals.getSavingsGoals());
//                } else {
//                    view.showError();
//                }
//            }
//        });
    }
}
