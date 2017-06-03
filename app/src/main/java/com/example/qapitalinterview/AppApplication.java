package com.example.qapitalinterview;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
