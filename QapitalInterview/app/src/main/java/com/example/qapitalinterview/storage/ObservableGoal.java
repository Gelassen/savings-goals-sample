package com.example.qapitalinterview.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.qapitalinterview.converters.SavingGoalsToContentValuesConverter;
import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by John on 11/1/2016.
 */

public class ObservableGoal {
    private ContentResolver cr;

    public ObservableGoal(Context context) {
        this.cr = context.getContentResolver();
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
