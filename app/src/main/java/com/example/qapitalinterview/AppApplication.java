package com.example.qapitalinterview;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.qapitalinterview.di.ApiModule;
import com.example.qapitalinterview.di.AppComponent;
import com.example.qapitalinterview.di.DaggerAppComponent;

public class AppApplication extends Application {

    protected static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = buildComponent();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .apiModule(new ApiModule(this))
                .build();
    }
}
