package com.example.qapitalinterview.di;


import com.example.qapitalinterview.fake.TestSampleActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {TestViewModule.class})
/*package*/ interface TestViewComponent extends ViewComponent {
    void inject(TestSampleActivity entity);
}
