package com.example.qapitalinterview.model;

import rx.Observable;


public interface IModel {

    Observable<SavingsGoals> getSavingGoals();

    @Deprecated
    Observable<SavingsGoals> cacheSavingGoals();

    Observable<Feeds> cacheUserFeeds(final int goalId);

    Observable<SavingRules> cacheSavingsRules();

}
