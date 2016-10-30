package com.example.qapitalinterview.model;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.api.IApi;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by John on 10/30/2016.
 */

public class Model implements IModel{

    private IApi api = ApiModule.getApiInterface();

    @Override
    public Observable<SavingsGoals> getSavingGoals() {
        return api.getSavingsGoals()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
