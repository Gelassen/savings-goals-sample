package com.example.qapitalinterview.presenter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.converters.CursorToFeedsConverter;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.converters.CursorToSavingsRuleConverter;
import com.example.qapitalinterview.interactor.GoalDetailsInteractor;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.IModel;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.storage.Contract;
import com.example.qapitalinterview.view.GoalDetailsActivity;
import com.example.qapitalinterview.view.IGoalDetailsView;
import com.example.qapitalinterview.view.adapters.AchievementsAdapter;
import com.example.qapitalinterview.view.adapters.FilterAdapter;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class GoalDetailsPresenter implements IGoalDetailsPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int TOKEN_GOAL = 0;
    private static final int TOKEN_GOAL_DETAILS = 1;
    private static final int TOKEN_GOAL_SAVINGS_RULES = 2;
    private static final int TOKEN_GOAL_SUM_OF_ITEMS = 3;

    private Activity context;
    private IModel model;
    private IGoalDetailsView view;
    private GoalDetailsInteractor interactor;
    private Subscription subscription = Subscriptions.empty();

    public GoalDetailsPresenter(Activity context, IModel model, IGoalDetailsView view) {
        this.context = context;
        this.model = model;
        this.view = view;
        this.interactor = new GoalDetailsInteractor();
    }

    @Override
    public void onStop() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onUploadData(final int goalId) {
    }

    @Override
    public void onCheckCachedData(LoaderManager manager, Bundle args) {
        manager.restartLoader(TOKEN_GOAL, args, this);
        manager.restartLoader(TOKEN_GOAL_SAVINGS_RULES, args, this);
        manager.restartLoader(TOKEN_GOAL_SUM_OF_ITEMS, args, this);
    }

    @Override
    public void onApplyFilter(LoaderManager manager, Bundle args) {
        manager.restartLoader(TOKEN_GOAL_DETAILS, args, this);
    }

    @Override
    public void onUploadDetails(int goalId) {
        model.cacheUserFeeds(goalId).subscribe(new Observer<Feeds>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to get feeds", e);
                view.showError();
            }

            @Override
            public void onNext(Feeds feeds) {

//                view.showData();
            }
        });

        model.cacheSavingsRules().subscribe(new Observer<SavingRules>() {
            @Override
            public void onCompleted() {
                // no op
            }

            @Override
            public void onError(Throwable e) {
                Log.e(App.TAG, "Failed to get savings rules", e);
                view.showError();
            }

            @Override
            public void onNext(SavingRules savingRules) {
                // no op, data will come from cache
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri;
        String selection;
        String[] selectionArgs;
        final String goalId = args.getString(GoalDetailsActivity.EXTRA_GOAL_ID);
        switch (id) {
            case TOKEN_GOAL:
                uri = Contract.contentUri(Contract.GoalTable.class);
                selection = Contract.GoalTable.ID + "=?";
                selectionArgs = new String[] { goalId };
                break;
            case TOKEN_GOAL_DETAILS:
                uri = Contract.contentUri(Contract.FeedTable.class);
                boolean isFilterApplied = args.getBoolean(GoalDetailsActivity.EXTRA_FILTER_APPLIED);
                selection = isFilterApplied ?
                        Contract.FeedTable.GOALD_ID + "=?" + " AND " + Contract.FeedTable.SAVINGS_RULE_ID + "=?"
                        : Contract.FeedTable.GOALD_ID + "=?" ;
                selectionArgs = isFilterApplied ?
                        new String[] { goalId, args.getString(GoalDetailsActivity.EXTRA_FILTER_ID) }
                        : new String[] { goalId };
                break;
            case TOKEN_GOAL_SAVINGS_RULES:
                uri = Contract.contentUri(Contract.SavingsRuleTable.class);
                selection = null;
                selectionArgs = null;
                break;
            case TOKEN_GOAL_SUM_OF_ITEMS:
                uri = Contract.contentUri(Contract.FeedTable.class);
                selection = Contract.FeedTable.GOALD_ID + "=?" + " AND " + Contract.FeedTable.TIMESTAMP + ">=?";
                selectionArgs = new String[] { goalId, interactor.getStartOfTheWeek() };
                break;
            default:
                throw new IllegalArgumentException("Do you add support of this loader? " + id);
        }
        return new CursorLoader(context, uri, null, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(App.TAG, "Data " + (data == null ? "cursor is null" : data.getCount()));
        switch (loader.getId()) {
            case TOKEN_GOAL:
                CursorToSavingGoalsConverter converter = new CursorToSavingGoalsConverter();
                List<SavingsGoal> goals = converter.convert(data);
                view.showGoal(goals.get(0));
                break;
            case TOKEN_GOAL_DETAILS:
                CursorToFeedsConverter feedsConverter = new CursorToFeedsConverter();
                List<Feed> feeds = feedsConverter.convert(data);
                view.showAchievements(feeds);
                break;
            case TOKEN_GOAL_SAVINGS_RULES:
                CursorToSavingsRuleConverter ruleConverter = new CursorToSavingsRuleConverter();
                List<SavingsRule> rules = ruleConverter.convert(data);
                view.showFilters(rules);
                break;
            case TOKEN_GOAL_SUM_OF_ITEMS:
                feedsConverter = new CursorToFeedsConverter();
                feeds = feedsConverter.convert(data);
                view.showWeeklyProgress(interactor.getAchievements(feeds));
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // no op
    }
}
