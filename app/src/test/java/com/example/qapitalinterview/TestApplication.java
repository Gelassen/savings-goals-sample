package com.example.qapitalinterview;


import com.example.qapitalinterview.di.AppComponent;
import com.example.qapitalinterview.di.DaggerTestComponent;

public class TestApplication extends AppApplication {

    @Override
    protected AppComponent buildComponent() {
        return DaggerTestComponent.builder().build();
    }
}
