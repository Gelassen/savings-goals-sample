package com.example.qapitalinterview;


import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.qapitalinterview.di.ApiModule;
import com.example.qapitalinterview.di.AppComponent;
import com.example.qapitalinterview.di.DaggerAppComponent;
import com.example.qapitalinterview.di.IComponent;

public class AppApplication extends Application {

    protected static IComponent appComponent;

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

    public static IComponent getAppComponent() {
        return appComponent;
    }

    protected IComponent buildComponent() {
        return DaggerAppComponent.builder()
                .apiModule(new ApiModule(this))
                .build();
    }
}
