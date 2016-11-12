package com.example.qapitalinterview.model;

import android.content.Context;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.api.IApi;
import com.example.qapitalinterview.storage.ObservableGoal;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by John on 10/30/2016.
 */

public class Model implements IModel{

    private IApi api = ApiModule.getApiInterface();
    private ObservableGoal observableGoal;

    private Context context;

    public Model(Context context) {
        this.context = context;
        this.observableGoal = new ObservableGoal(context);
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
}
