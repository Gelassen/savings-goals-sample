package com.example.qapitalinterview;


import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

public class TestApplication extends AppApplication {

    // TODO init the test component instead of common one

    @Override
    protected void attachBaseContext(Context base) {
        Log.d(App.TAG, "attachBaseContext");
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
