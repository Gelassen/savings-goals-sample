package com.example.qapitalinterview.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.converters.CursorToFeedsConverter;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.converters.SavingGoalsToContentValuesConverter;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Timestamped;

/**
 * Created by John on 11/1/2016.
 */

public class ObservableGoal {
    private ContentResolver cr;

    public ObservableGoal(Context context) {
        this.cr = context.getContentResolver();
    }

    public Observable<SavingsGoals> saveAll(long timestamp, List<SavingsGoal> data) {
        Log.d(App.FLOW, "saveAll:explicit");
        return Observable.create(new Observable.OnSubscribe<SavingsGoals>() {

            @Override
            public void call(Subscriber<? super SavingsGoals> subscriber) {
                subscriber.onStart();
                Log.d(App.FLOW, "saveAll:implicit");

                Uri uri = Contract.contentUri(Contract.GoalTable.class);
                SavingGoalsToContentValuesConverter converter = new SavingGoalsToContentValuesConverter();
                converter.setTimestamp(timestamp);
                cr.bulkInsert(uri, converter.convert(data));

                SavingsGoals result = new SavingsGoals();
                result.setSavingsGoals(data);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<Timestamped<SavingsGoals>> saveAllWithTimestamp(long timestamp, List<SavingsGoal> data) {
        Log.d(App.FLOW, "saveAll:explicit");
        return Observable.create(new Observable.OnSubscribe<Timestamped<SavingsGoals>>() {

            @Override
            public void call(Subscriber<? super Timestamped<SavingsGoals>> subscriber) {
                subscriber.onStart();
                Log.d(App.FLOW, "saveAll:implicit");

                Uri uri = Contract.contentUri(Contract.GoalTable.class);
                SavingGoalsToContentValuesConverter converter = new SavingGoalsToContentValuesConverter();
                converter.setTimestamp(timestamp);
                cr.bulkInsert(uri, converter.convert(data));

                SavingsGoals result = new SavingsGoals();
                result.setSavingsGoals(data);

                subscriber.onNext(new Timestamped<>(timestamp, result));
                subscriber.onCompleted();
            }
        });
    }

    public Observable<SavingsGoals> saveAll(List<SavingsGoal> data) {
        return saveAll(System.currentTimeMillis(), data);
    }

    public Observable<Timestamped<SavingsGoals>> getSavingsGoals() {
        Log.d(App.FLOW, "getSavingsGoals:explicit");
        return Observable.create(new Observable.OnSubscribe<Timestamped<SavingsGoals>>() {

            @Override
            public void call(Subscriber<? super Timestamped<SavingsGoals>> subscriber) {
                subscriber.onStart();
                Log.d(App.FLOW, "getSavingsGoals:implicit");

                Uri uri = Contract.contentUri(Contract.GoalTable.class);
                Cursor cursor = cr.query(uri, null, null, null, null);
                CursorToSavingGoalsConverter converter = new CursorToSavingGoalsConverter();
                List<SavingsGoal> goals = converter.convert(cursor);

                if (cursor != null) {
                    cursor.close();
                }

                long timestamp = goals.size() == 0 ? 0 : goals.get(0).getTimestamp();

                SavingsGoals savingsGoals = new SavingsGoals();
                Timestamped<SavingsGoals> result = new Timestamped<SavingsGoals>(timestamp, savingsGoals);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<SavingsGoal> getSavingsGoal(final int goalId) {
        return Observable.create(new Observable.OnSubscribe<SavingsGoal>() {

            @Override
            public void call(Subscriber<? super SavingsGoal> subscriber) {
                subscriber.onStart();

                Uri uri = Contract.contentUri(Contract.GoalTable.class);
                String selection = Contract.GoalTable.ID + "=?";
                String[] selectionArgs = new String[] { String.valueOf(goalId) };

                Cursor cursor = cr.query(uri, null, selection, selectionArgs, null);
                CursorToSavingGoalsConverter converter = new CursorToSavingGoalsConverter();
                List<SavingsGoal> goals = converter.convert(cursor);

                if (cursor != null) {
                    cursor.close();
                }

                subscriber.onNext(goals.size() == 0 ? new SavingsGoal() : goals.get(0));
                subscriber.onCompleted();
            }
        });
    }
}
