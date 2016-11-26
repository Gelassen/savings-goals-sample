package com.example.qapitalinterview;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class TestActivity extends AppCompatActivity {

    private RecyclerView list;

    private Subscription subscription = Subscriptions.empty();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        IModel model = new Model(this);

        list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(new AchievementsAdapter());

        findViewById(R.id.click_me_to_clean_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Uri uri = Contract.contentUri(Contract.GoalTable.class);
                final int rows = getContentResolver().delete(uri, null, null);
                Log.d(App.TAG, "Deleted rows: " + rows);
            }
        });
        findViewById(R.id.click_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!subscription.isUnsubscribed()) {
                    subscription.unsubscribe();
                }

                model.getSavingGoals().subscribe(new Observer<SavingsGoals>() {
                    @Override
                    public void onCompleted() {
                        // no op
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(App.TAG, "Failed to get goals", e);
                    }

                    @Override
                    public void onNext(SavingsGoals savingsGoals) {
                        Log.d(App.TAG, "onNext: " + savingsGoals.getSavingsGoals().size());
                    }
                });
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
