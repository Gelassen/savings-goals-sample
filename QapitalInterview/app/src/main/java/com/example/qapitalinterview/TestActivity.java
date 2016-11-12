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

import com.example.qapitalinterview.converters.CursorToFeedsConverter;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;

import java.util.List;

public class TestActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);

        list = (RecyclerView) findViewById(R.id.list);
        list.setAdapter(new AchievementsAdapter());


        getSupportLoaderManager().restartLoader(0, Bundle.EMPTY, this);
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
