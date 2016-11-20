package com.example.qapitalinterview;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.qapitalinterview.converters.CursorToFeedsConverter;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;

import java.util.List;

import rx.Observer;
import rx.schedulers.Schedulers;
import rx.schedulers.Timestamped;

public class TestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(new AchievementsAdapter());


        getSupportLoaderManager().restartLoader(0, Bundle.EMPTY, this);


        IModel model = new Model(this);
//        model.cacheSavingGoals().subscribe(new Observer<SavingsGoals>() {
//            @Override
//            public void onCompleted() {
//                // no op
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(App.TAG, "Error", e);
//            }
//
//            @Override
//            public void onNext(SavingsGoals savingsGoals) {
//                Log.d(App.TAG, "onNext: " + savingsGoals.getSavingsGoals().size());
//            }
//        });
        model.getSavingGoals().subscribe(new Observer<Timestamped<SavingsGoals>>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to get goals from web server", e);
            }

            @Override
            public void onNext(Timestamped<SavingsGoals> savingsGoalsTimestamped) {
                Log.d(App.TAG, "Savings goals: " + savingsGoalsTimestamped.getValue().getSavingsGoals().size());
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, Contract.contentUri(Contract.FeedTable.class), null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        CursorToFeedsConverter converter = new CursorToFeedsConverter();
        List<Feed> feedList = converter.convert(data);
        AchievementsAdapter adapter = (AchievementsAdapter) list.getAdapter();
        adapter.update(feedList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
