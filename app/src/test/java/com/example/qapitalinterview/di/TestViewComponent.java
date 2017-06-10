package com.example.qapitalinterview.di;


import com.example.qapitalinterview.GoalsViewTest;
import com.example.qapitalinterview.fake.TestSampleActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestViewModule.class})
public interface TestViewComponent extends ViewComponent {
    void inject(TestSampleActivity entity);
    void inject(GoalsViewTest entity);
}
