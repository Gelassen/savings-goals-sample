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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.R;
import com.example.qapitalinterview.converters.CursorToFeedsConverter;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.converters.CursorToSavingsRuleConverter;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.presenter.GoalDetailsPresenter;
import com.example.qapitalinterview.presenter.IGoalDetailsPresenter;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;
import com.example.qapitalinterview.view.adapters.FilterAdapter;

import java.util.List;


/**
 * Created by John on 10/30/2016.
 */

public class GoalDetailsActivity extends BaseActivity implements
        IGoalDetailsView,
        LoaderManager.LoaderCallbacks<Cursor>  {

    private static final String EXTRA_GOAL_ID = "EXTRA_GOAL_ID";

    private static final int TOKEN_GOAL = 0;
    private static final int TOKEN_GOAL_DETAILS = 1;
    private static final int TOKEN_GOAL_SAVINGS_RULES = 2;

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

    private ImageView goalImageView;
    private RecyclerView listRules;
    private RecyclerView listAchievements;
    private TextView totalAchivements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setContentView(R.layout.activity_goal_details);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        goalImageView = (ImageView) findViewById(R.id.goal_image);
        totalAchivements = (TextView) findViewById(R.id.total_achievements);

        listRules = (RecyclerView) findViewById(R.id.list_filter);
        listRules.setAdapter(new FilterAdapter());
        listRules.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        listAchievements = (RecyclerView) findViewById(R.id.list_achievements);
        listAchievements.setAdapter(new AchievementsAdapter());
        listAchievements.setLayoutManager(new LinearLayoutManager(this));

        presenter = new GoalDetailsPresenter(new Model(this), this);
        presenter.onUploadDetails(getIntent().getIntExtra(EXTRA_GOAL_ID, 0));

        getSupportLoaderManager().restartLoader(TOKEN_GOAL, Bundle.EMPTY, this);
        getSupportLoaderManager().restartLoader(TOKEN_GOAL_DETAILS, Bundle.EMPTY, this);
        getSupportLoaderManager().restartLoader(TOKEN_GOAL_SAVINGS_RULES, Bundle.EMPTY, this);
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
        Uri uri;
        String selection;
        String[] selectionArgs;
        final String goalId = String.valueOf(getIntent().getIntExtra(EXTRA_GOAL_ID, 0));
        switch (id) {
            case TOKEN_GOAL:
                uri = Contract.contentUri(Contract.GoalTable.class);
                selection = Contract.GoalTable.ID + "=?";
                selectionArgs = new String[] { goalId };
                break;
            case TOKEN_GOAL_DETAILS:
                uri = Contract.contentUri(Contract.FeedTable.class);
                selection = Contract.FeedTable.GOALD_ID + "=?";
                selectionArgs = new String[] { goalId };
                break;
            case TOKEN_GOAL_SAVINGS_RULES:
                uri = Contract.contentUri(Contract.SavingsRuleTable.class);
                selection = null;
                selectionArgs = null;
                break;
            default:
                throw new IllegalArgumentException("Do you add support of this loader? " + id);
        }
        return new CursorLoader(this, uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(App.TAG, "Data " + (data == null ? "cursor is null" : data.getCount()));
        switch (loader.getId()) {
            case TOKEN_GOAL:
                CursorToSavingGoalsConverter converter = new CursorToSavingGoalsConverter();
                List<SavingsGoal> goals = converter.convert(data);
                break;
            case TOKEN_GOAL_DETAILS:
                Log.d("DETAILS", "Details: " + data.getCount());
                CursorToFeedsConverter feedsConverter = new CursorToFeedsConverter();
                List<Feed> feeds = feedsConverter.convert(data);
                AchievementsAdapter achievementsAdapter = (AchievementsAdapter) listAchievements.getAdapter();
                achievementsAdapter.update(feeds);
                break;
            case TOKEN_GOAL_SAVINGS_RULES:
                CursorToSavingsRuleConverter ruleConverter = new CursorToSavingsRuleConverter();
                List<SavingsRule> rules = ruleConverter.convert(data);
                FilterAdapter adapter = (FilterAdapter) listRules.getAdapter();
                adapter.updateModel(rules);
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
