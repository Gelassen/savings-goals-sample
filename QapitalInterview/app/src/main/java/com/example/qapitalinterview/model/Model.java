package com.example.qapitalinterview.model;

import android.content.Context;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.storage.ObservableFeed;
import com.example.qapitalinterview.storage.ObservableGoal;
import com.example.qapitalinterview.storage.ObservableSavingRule;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by John on 10/30/2016.
 */

public class Model implements IModel{

    private IApi api = ApiModule.getApiInterface();
    private ObservableGoal observableGoal;
    private ObservableFeed observableFeed;
    private ObservableSavingRule observableSavingRule;

    private Context context;

    public Model(Context context) {
        this.context = context;
        this.observableGoal = new ObservableGoal(context);
        this.observableFeed = new ObservableFeed(context);
        this.observableSavingRule = new ObservableSavingRule(context);
    }

    @Override
    public Observable<SavingsGoals> getSavingGoals() {
        return api.getSavingsGoals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<SavingsGoals> cacheSavingGoals() {
        return api.getSavingsGoals()
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<SavingsGoals, Observable<SavingsGoals>>() {
                    @Override
                    public Observable<SavingsGoals> call(SavingsGoals savingsGoals) {
                        return observableGoal.saveAll(savingsGoals.getSavingsGoals());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public Observable<Feeds> cacheUserFeeds(int goalId) {
        return api.getSavingsGoalFeed(goalId)
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<Feeds, Observable<Feeds>>() {
                    @Override
                    public Observable<Feeds> call(Feeds feeds) {
                        return observableFeed.saveAll(feeds.getFeed(), goalId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<SavingRules> cacheSavingsRules() {
        return api.getSavingsRules()
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<SavingRules, Observable<SavingRules>>() {
                    @Override
                    public Observable<SavingRules> call(SavingRules savingRules) {
                        return observableSavingRule.saveAll(savingRules.getSavingsRules());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }
}
