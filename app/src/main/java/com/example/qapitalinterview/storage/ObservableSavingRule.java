package com.example.qapitalinterview.storage;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import com.example.qapitalinterview.converters.FeedsToContentValuesConverter;
import com.example.qapitalinterview.converters.SavingRulesToContentValuesConverter;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.model.Feeds;
import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsRule;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by John on 11/1/2016.
 */

public class ObservableSavingRule {
    private ContentResolver cr;

    public ObservableSavingRule(Context context) {
        this.cr = context.getContentResolver();
    }

    public Observable<SavingRules> saveAll(List<SavingsRule> data) {
        return Observable.create(new Observable.OnSubscribe<SavingRules>() {

            @Override
            public void call(Subscriber<? super SavingRules> subscriber) {
                subscriber.onStart();

                Uri uri = Contract.contentUri(Contract.SavingsRuleTable.class);
                SavingRulesToContentValuesConverter converter = new SavingRulesToContentValuesConverter();
                cr.bulkInsert(uri, converter.convert(data));

                SavingRules result = new SavingRules();
                result.setSavingsRules(data);

                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        });
    }
}
