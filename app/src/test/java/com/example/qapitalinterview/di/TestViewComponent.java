package com.example.qapitalinterview.di;



import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestViewModule.class})
public interface TestViewComponent extends ViewComponent {
    void inject(Object object);
}
