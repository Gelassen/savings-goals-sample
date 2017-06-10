package com.example.qapitalinterview.di;


import com.example.qapitalinterview.BaseTest;
import com.example.qapitalinterview.GoalsPresenterTest;
import com.example.qapitalinterview.ModelTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ModelTestModule.class})
public interface TestComponent extends AppComponent{
    void inject(BaseTest entity);
    void inject(ModelTest entity);
    void inject(GoalsPresenterTest entity);
}
