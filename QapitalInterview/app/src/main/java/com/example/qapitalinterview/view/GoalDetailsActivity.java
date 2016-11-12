package com.example.qapitalinterview.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.presenter.GoalDetailsPresenter;
import com.example.qapitalinterview.presenter.IGoalDetailsPresenter;
import com.example.qapitalinterview.storage.Contract;


/**
 * Created by John on 10/30/2016.
 */

public class GoalDetailsActivity extends BaseActivity implements
        IGoalDetailsView,
        LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String EXTRA_GOAL_ID = "EXTRA_GOAL_ID";

    public static void start(Context context, int goalId) {
        Intent intent = new Intent(context, GoalDetailsActivity.class);
        intent.putExtra(EXTRA_GOAL_ID, goalId);
        context.startActivity(intent);
    }

    private IGoalDetailsPresenter presenter;

    @Deprecated
    public static void startTransition(Activity context, View transitView, String transitName) {
        Intent intent = new Intent(context, GoalDetailsActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context, transitView, transitName);
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_goal_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new GoalDetailsPresenter(new Model(this), this);
        presenter.onUploadDetails(getIntent().getIntExtra(EXTRA_GOAL_ID, 0));
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
    }

    @Override
    public void showData() {
        getSupportLoaderManager().restartLoader(0, Bundle.EMPTY, this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError() {
        Toast.makeText(this, getString(R.string.error_goals), Toast.LENGTH_SHORT).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = Contract.contentUri(Contract.FeedTable.class);
        String selection = Contract.FeedTable.GOALD_ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(getIntent().getIntExtra(EXTRA_GOAL_ID, 0)) };
        return new CursorLoader(this, uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(App.TAG, "Data " + (data == null ? "cursor is null" : data.getCount()));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
