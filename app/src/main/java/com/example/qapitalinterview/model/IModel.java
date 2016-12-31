package com.example.qapitalinterview.model;

import com.example.qapitalinterview.storage.ObservableGoal;

import java.util.List;

import rx.Observable;
import rx.schedulers.Timestamped;

/**
 * Created by John on 10/30/2016.
 */

public interface IModel {

    Observable<SavingsGoals> getSavingGoals();

    @Deprecated
    Observable<SavingsGoals> cacheSavingGoals();

    Observable<Feeds> cacheUserFeeds(final int goalId);

    Observable<SavingRules> cacheSavingsRules();

}
