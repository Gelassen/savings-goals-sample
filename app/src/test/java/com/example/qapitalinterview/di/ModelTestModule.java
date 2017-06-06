package com.example.qapitalinterview.di;


import com.example.qapitalinterview.App;
import com.example.qapitalinterview.api.IApi;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;

@Module
/*package*/ class ModelTestModule {

    @Provides
    @Singleton
    /*package*/ static IApi getApiInterface() {
        return mock(IApi.class);
    }

    @Provides
    @Singleton
    @Named(App.Const.UI_THREAD)
    /*package*/ static Scheduler provideSchedulerUI() {
        return Schedulers.immediate();
    }

    @Provides
    @Singleton
    @Named(App.Const.IO_THREAD)
    /*package*/ static Scheduler provideSchedulerIO() {
        return Schedulers.immediate();
    }
}
