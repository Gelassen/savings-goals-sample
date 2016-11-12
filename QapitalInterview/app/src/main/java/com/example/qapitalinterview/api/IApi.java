package com.example.qapitalinterview.api;

import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.model.User;

import java.util.List;

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

    @GET("savingsrules")
    Observable<SavingRules> getSavingsRules();

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") int id);
}
