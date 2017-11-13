package com.example.qapitalinterview.integration.di;


import com.example.qapitalinterview.di.AppComponent;
import com.example.qapitalinterview.di.IComponent;
import com.example.qapitalinterview.integration.GoalsPresenterIntegrationTest;
import com.example.qapitalinterview.integration.ModelIntegrationTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {IntegrationModule.class})
public interface IntegrationComponent /*extends AppComponent */{
    void inject(ModelIntegrationTest entity);
    void inject(GoalsPresenterIntegrationTest goalsPresenterIntegrationTest);
}
