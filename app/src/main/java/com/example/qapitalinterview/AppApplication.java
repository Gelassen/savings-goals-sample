package com.example.qapitalinterview;


import android.app.Application;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.components.AppComponent;
import com.example.qapitalinterview.components.DaggerAppComponent;

public class AppApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
