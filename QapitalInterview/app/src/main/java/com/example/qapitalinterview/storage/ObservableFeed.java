package com.example.qapitalinterview.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.qapitalinterview.converters.CursorToFeedsConverter;
import com.example.qapitalinterview.converters.FeedsToContentValuesConverter;
import com.example.qapitalinterview.converters.SavingGoalsToContentValuesConverter;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by John on 11/1/2016.
 */

public class ObservableFeed {
    private ContentResolver cr;

    public ObservableFeed(Context context) {
        this.cr = context.getContentResolver();
    }

    public Observable<Feeds> saveAll(List<Feed> data, final int goalId) {
        return Observable.create(new Observable.OnSubscribe<Feeds>() {

            @Override
            public void call(Subscriber<? super Feeds> subscriber) {
                subscriber.onStart();

                Uri uri = Contract.contentUri(Contract.FeedTable.class);
                cr.delete(uri, Contract.FeedTable.GOALD_ID + "=?", new String[] { String.valueOf(goalId) });
                FeedsToContentValuesConverter converter = new FeedsToContentValuesConverter(goalId);
                cr.bulkInsert(uri, converter.convert(data));

                Feeds result = new Feeds();
                result.setFeed(data);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Feeds> getAllFeedsForGoal(final int goalId, final int filterId, final boolean isFilterApplied) {
        return Observable.create(new Observable.OnSubscribe<Feeds>() {

            @Override
            public void call(Subscriber<? super Feeds> subscriber) {
                subscriber.onStart();

                Uri uri = Contract.contentUri(Contract.FeedTable.class);
                String selection = isFilterApplied ?
                        Contract.FeedTable.GOALD_ID + "=?" + " AND " + Contract.FeedTable.SAVINGS_RULE_ID + "=?"
                        : Contract.FeedTable.GOALD_ID + "=?" ;
                String[] selectionArgs = isFilterApplied ?
                        new String[] { String.valueOf(goalId), String.valueOf(filterId) }
                        : new String[] { String.valueOf(goalId) };

                // prepare cursor
                Cursor cursor = cr.query(uri, null, selection, selectionArgs, null);
                CursorToFeedsConverter converter = new CursorToFeedsConverter();
                List<Feed> feeds = converter.convert(cursor);
                if (cursor!= null) {
                    cursor.close();
                }

                // prepare result
                Feeds result = new Feeds();
                result.setFeed(feeds);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }
}
