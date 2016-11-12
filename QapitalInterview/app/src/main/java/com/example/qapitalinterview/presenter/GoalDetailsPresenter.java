package com.example.qapitalinterview.presenter;


import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.view.IGoalDetailsView;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class GoalDetailsPresenter implements IGoalDetailsPresenter {

    private IModel model;
    private IGoalDetailsView view;
    private Subscription subscription = Subscriptions.empty();

    public GoalDetailsPresenter(IModel model, IGoalDetailsView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onUploadDetails(int goalId) {
        model.cacheUserFeeds(goalId).subscribe(new Observer<Feeds>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to get feeds", e);
                view.showError();
            }

            @Override
            public void onNext(Feeds feeds) {
                view.showData();
            }
        });

        model.cacheSavingsRules().subscribe(new Observer<SavingRules>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to get savings rules", e);
                view.showError();
            }

            @Override
            public void onNext(SavingRules savingRules) {
                view.showData();
            }
        });
    }

    @Override
    public void onUpdateModel(SavingsGoal goal) {

    }

    @Override
    public void onUpdateModel(List<Feed> feeds) {

    }

    @Override
    public void onUpdateFilter(List<SavingsRule> rules) {

    }
}
