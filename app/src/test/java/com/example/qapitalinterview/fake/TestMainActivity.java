package com.example.qapitalinterview.fake;


import com.example.qapitalinterview.di.DaggerTestViewComponent;
import com.example.qapitalinterview.di.TestViewModule;
import com.example.qapitalinterview.view.MainActivity;

public class TestMainActivity extends MainActivity {

    @Override
    protected void initViewComponent() {
        viewComponent = DaggerTestViewComponent.builder()
                .testViewModule(new TestViewModule())
                .build();
        viewComponent.inject(this);
    }

}
