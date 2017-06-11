package com.example.qapitalinterview.di;


import com.example.qapitalinterview.presenter.IGoalsPresenter;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static org.mockito.Mockito.mock;

@Module
public class TestViewModule {

    @Singleton
    @Provides
    IGoalsPresenter getPresenter() {
        return mock(SavingsGoalPresenter.class);
    }
}
