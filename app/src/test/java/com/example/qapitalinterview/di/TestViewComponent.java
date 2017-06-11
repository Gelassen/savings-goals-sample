package com.example.qapitalinterview.di;


import com.example.qapitalinterview.GoalsViewTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestViewModule.class})
public interface TestViewComponent extends ViewComponent {
    void inject(GoalsViewTest entity);
}
