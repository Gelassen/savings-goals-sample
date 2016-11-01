package com.example.qapitalinterview.model;

import android.content.Context;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.storage.ObservableGoal;

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

    public Model(Context context) {
        observableGoal = new ObservableGoal(context);
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
                        observableGoal.saveAll(savingsGoals.getSavingsGoals());
                        return Observable.just(savingsGoals);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

    }
}
