package com.example.qapitalinterview.storage;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.converters.CursorToSavingGoalsConverter;
import com.example.qapitalinterview.model.SavingsGoal;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by John on 10/30/2016.
 */

public class ObservableDbHelper {
    private PublishSubject<List<SavingsGoal>> subject = PublishSubject.create();
    private GoalRepo goalRepo;

    public ObservableDbHelper(Context context) {
        goalRepo = new GoalRepo(context);
    }

    public Observable<List<SavingsGoal>> getObservable() {
        return rx.Observable.fromCallable(this::getSavingsGoals)
                .concatWith(subject);
    }

    public void insertList(List<SavingsGoal> data) {
        try {
            goalRepo.open();
            for (SavingsGoal goal : data) {
                goalRepo.addGoal(goal);
            }
        } catch (Exception ex) {
            Log.e(App.TAG, "Failed to insert new goals", ex);
        } finally {
            goalRepo.close();
        }
    }

    private List<SavingsGoal> getSavingsGoals() {
        List<SavingsGoal> result = new ArrayList<>();
        try {
            goalRepo.open();
            CursorToSavingGoalsConverter converter = new CursorToSavingGoalsConverter();
            Cursor cursor = goalRepo.getAllGoals();
            result = converter.convert(cursor);
        } catch (Exception ex) {
            Log.e(App.TAG, "Failed to obtain goals", ex);
        } finally {
            goalRepo.close();
        }
        return result;
    }
}
