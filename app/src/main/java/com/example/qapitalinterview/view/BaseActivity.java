package com.example.qapitalinterview.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.qapitalinterview.R;

public abstract class BaseActivity extends AppCompatActivity {

    protected void init(boolean isEnableHomeButton) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar =  getSupportActionBar();
            if (actionBar != null) {
                getSupportActionBar().setHomeButtonEnabled(isEnableHomeButton);
                getSupportActionBar().setDisplayHomeAsUpEnabled(isEnableHomeButton);
            }
        }
    }

}
