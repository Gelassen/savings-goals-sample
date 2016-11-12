package com.example.qapitalinterview.api;

import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.model.User;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by John on 10/30/2016.
 */

public interface IApi {

    @GET("savingsgoals")
    Observable<SavingsGoals> getSavingsGoals();

    @GET("savingsgoals/{id}/feed")
    Observable<Feeds> getSavingsGoalFeed(@Path("id") int id);

    @GET("savingsrule")
    Observable<SavingRules> getSavingsRule();

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") int id);
}
