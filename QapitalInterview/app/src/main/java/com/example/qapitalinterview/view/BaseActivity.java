package com.example.qapitalinterview.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.qapitalinterview.R;

/**
 * Created by John on 10/30/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }
}
