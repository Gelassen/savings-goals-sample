package com.example.qapitalinterview.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.converters.SavingGoalsToContentValuesConverter;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by John on 11/1/2016.
 */

public class ObservableGoal {
    private GoalRepo goalRepo;
    private ContentResolver cr;

    public ObservableGoal(Context context) {
        this.goalRepo = new GoalRepo(context);
        this.cr = context.getContentResolver();
    }

    public Observable<List<SavingsGoal>> getAll() {
        return Observable.create(new Observable.OnSubscribe<List<SavingsGoal>>() {
            @Override
            public void call(Subscriber<? super List<SavingsGoal>> subscriber) {
                subscriber.onStart();

                List<SavingsGoal> result = new ArrayList<>();
                Cursor cursor = null;
                try {
                    cursor = goalRepo.getAllGoals();
                    result = new CursorToSavingGoalsConverter()
                            .convert(cursor);
                } catch (SQLException ex) {
                    Log.e(App.TAG, "Failed to obtain goals", ex);
                    subscriber.onError(ex);
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<SavingsGoals> saveAll(List<SavingsGoal> data) {
        return Observable.create(new Observable.OnSubscribe<SavingsGoals>() {

            @Override
            public void call(Subscriber<? super SavingsGoals> subscriber) {
                subscriber.onStart();

                Uri uri = Contract.contentUri(Contract.GoalTable.class);
                SavingGoalsToContentValuesConverter converter = new SavingGoalsToContentValuesConverter();
                cr.bulkInsert(uri, converter.convert(data));

                SavingsGoals result = new SavingsGoals();
                result.setSavingsGoals(data);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }
}
