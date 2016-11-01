package com.example.qapitalinterview.storage;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.model.SavingsGoal;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by John on 11/1/2016.
 */

public class ObservableGoal {
    private GoalRepo goalRepo;

    public ObservableGoal(Context context) {
        this.goalRepo = new GoalRepo(context);
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

    public Observable<List<SavingsGoal>> saveAll(List<SavingsGoal> data) {
        return Observable.create(new Observable.OnSubscribe<List<SavingsGoal>>() {

            @Override
            public void call(Subscriber<? super List<SavingsGoal>> subscriber) {
                subscriber.onStart();
                List<SavingsGoal> result = new ArrayList<>();
                try {
                    for (SavingsGoal goal : data) {
                        long l = goalRepo.addGoal(goal);
                        Log.d(App.TAG, "test insert: " + l);
                    }
                } catch (SQLException ex) {
                    Log.e(App.TAG, "Failed to save data", ex);
                    subscriber.onError(ex);
                }

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }
}
