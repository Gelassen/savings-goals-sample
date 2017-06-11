package com.example.qapitalinterview.di;


import android.app.Activity;

import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent extends IComponent{
    void inject(Activity activity);
    void inject(Model model);
    void inject(SavingsGoalPresenter entity);
}
