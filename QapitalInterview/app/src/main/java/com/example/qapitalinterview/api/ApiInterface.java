package com.example.qapitalinterview.api;

import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by John on 10/30/2016.
 */

public interface ApiInterface {

    @GET("savingsgoals")
    Observable<List<SavingsGoal>> getSavingsGoals();

    @GET("savingsgoals/{id}/feed")
    Observable<List<Feed>> getSavingsGoalFeed(@Path("id") int id);

    @GET("savingsrule")
    Observable<List<SavingsRule>> getSavingsRule();

    @GET("user/{id}")
    Observable<User> getUser(@Path("id") int id);
}
