package com.example.qapitalinterview.model;

import android.content.Context;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.AppApplication;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.di.AppComponent;
import com.example.qapitalinterview.storage.ObservableFeed;
import com.example.qapitalinterview.storage.ObservableGoal;
import com.example.qapitalinterview.storage.ObservableSavingRule;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Timestamped;


public class Model implements IModel{

    @Inject
    IApi api;

    private ObservableGoal observableGoal;
    private ObservableFeed observableFeed;
    private ObservableSavingRule observableSavingRule;

    @Inject
    @Named(App.Const.IO_THREAD)
    Scheduler ioThread;

    @Inject
    @Named(App.Const.UI_THREAD)
    Scheduler mainThread;

    private Context context;

    public Model(Context context) {
        ((AppComponent) AppApplication.getAppComponent()).inject(this);
        this.context = context;
        this.observableGoal = new ObservableGoal(context);
        this.observableFeed = new ObservableFeed(context);
        this.observableSavingRule = new ObservableSavingRule(context);
    }

    public IApi getApi() {
        return api;
    }

    @Override
    public Observable<SavingsGoals> getSavingGoals() {
        return api.getSavingsGoals()
                .publish(network ->
                        Observable.mergeDelayError(
                                observableGoal.getSavingsGoals().takeUntil(network),
                                network.flatMap(new Func1<SavingsGoals, Observable<SavingsGoals>>() {
                                    @Override
                                    public Observable<SavingsGoals> call(SavingsGoals savingsGoals) {
                                        return observableGoal.saveAll(savingsGoals.getSavingsGoals());
                                    }
                                })
                        )
                )
                .onErrorResumeNext(observableGoal.getSavingsGoals())
                .subscribeOn(ioThread)
                .observeOn(mainThread);
    }

    @Override
    public Observable<SavingsGoals> cacheSavingGoals() {
        return api.getSavingsGoals()
                .subscribeOn(ioThread)
                .flatMap(new Func1<SavingsGoals, Observable<SavingsGoals>>() {
                    @Override
                    public Observable<SavingsGoals> call(SavingsGoals savingsGoals) {
                        return observableGoal.saveAll(savingsGoals.getSavingsGoals());
                    }
                })
                .observeOn(mainThread);

    }

    @Override
    public Observable<Feeds> cacheUserFeeds(int goalId) {
        return api.getSavingsGoalFeed(goalId)
                .subscribeOn(ioThread)
                .flatMap(new Func1<Feeds, Observable<Feeds>>() {
                    @Override
                    public Observable<Feeds> call(Feeds feeds) {
                        return observableFeed.saveAll(feeds.getFeed(), goalId);
                    }
                })
                .observeOn(mainThread);
    }

    @Override
    public Observable<SavingRules> cacheSavingsRules() {
        return api.getSavingsRules()
                .subscribeOn(ioThread)
                .flatMap(new Func1<SavingRules, Observable<SavingRules>>() {
                    @Override
                    public Observable<SavingRules> call(SavingRules savingRules) {
                        return observableSavingRule.saveAll(savingRules.getSavingsRules());
                    }
                })
                .observeOn(mainThread);
    }

    @Deprecated
    public Observable<Timestamped<SavingsGoals>> getTestSavingsGoal() {
        return observableGoal
                .getTimestampedSavingsGoals()
                .subscribeOn(ioThread)
                .flatMap(new Func1<Timestamped<SavingsGoals>, Observable<Timestamped<SavingsGoals>>>() {
                    @Override
                    public Observable<Timestamped<SavingsGoals>> call(Timestamped<SavingsGoals> cachedData) {
                        Log.d(App.FLOW, "getTimestampedSavingsGoals");
                        return getGoalsFromBothSources()
                                .filter(filterResponse(cachedData));
                    }
                })
                .subscribeOn(mainThread);
    }

    @Deprecated
    private Func1<Timestamped<SavingsGoals>, Boolean> filterResponse(Timestamped<SavingsGoals> cachedData) {
        return new Func1<Timestamped<SavingsGoals>, Boolean>() {
            @Override
            public Boolean call(Timestamped<SavingsGoals> savingsGoals) {
                return savingsGoals != null
                        && cachedData != null
                        && cachedData.getTimestampMillis() < savingsGoals.getTimestampMillis()
                        && savingsGoals.getValue().getSavingsGoals().size() != 0;
            }
        };
    }

    @Deprecated
    private Observable<Timestamped<SavingsGoals>> getGoalsFromBothSources() {
        Log.d(App.FLOW, "getGoalsFromBothSources:explicit");
        return Observable.merge(
                observableGoal.getTimestampedSavingsGoals().subscribeOn(ioThread),
                api.getSavingsGoals()
                        .timestamp()
                        .flatMap(new Func1<Timestamped<SavingsGoals>, Observable<Timestamped<SavingsGoals>>>() {
                            @Override
                            public Observable<Timestamped<SavingsGoals>> call(Timestamped<SavingsGoals> savingsGoals) {
                                Log.d(App.FLOW, "getGoalsFromBothSources:implicit");
                                return observableGoal.saveAllWithTimestamp(savingsGoals.getTimestampMillis(), savingsGoals.getValue().getSavingsGoals());
                            }
                        }))
                .subscribeOn(ioThread);
    }

}
