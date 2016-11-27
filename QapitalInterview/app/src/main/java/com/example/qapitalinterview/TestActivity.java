package com.example.qapitalinterview;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.storage.ObservableFeed;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;

import rx.Observer;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_details_in_progres);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar =  getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        RecyclerView listAchievements = (RecyclerView) findViewById(R.id.list_achievements);
        listAchievements.setAdapter(new AchievementsAdapter());
        listAchievements.setLayoutManager(new LinearLayoutManager(this));

        ObservableFeed observableGoal = new ObservableFeed(this);
        observableGoal.getAllFeedsForGoal(1, 0, false).subscribe(new Observer<Feeds>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to consume goal", e);
            }

            @Override
            public void onNext(Feeds feeds) {
                Log.d(App.TAG, "Feeds " + feeds.getFeed().size());
                AchievementsAdapter adapter = (AchievementsAdapter) listAchievements.getAdapter();
                adapter.update(feeds.getFeed());
            }
        });
    }
}
