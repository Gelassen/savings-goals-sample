package com.example.qapitalinterview.di;


import android.content.Context;

import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;
import com.example.qapitalinterview.view.IGoalView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ViewModule {

    private Context context;
    private IGoalView view;

    public ViewModule(Context context, IGoalView view) {
        this.context = context;
        this.view = view;
    }

    @Singleton
    @Provides
    /*package*/ IGoalsPresenter getPresenter() {
        return new SavingsGoalPresenter(context, view);
    }

    @Singleton
    @Provides
    /*package*/ IModel getModel() {
        return new Model(context);
    }
}
