package com.example.qapitalinterview.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by John on 10/30/2016.
 */

public class GoalDetailsActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, GoalDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
