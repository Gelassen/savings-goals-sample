package com.example.qapitalinterview.converters;


import android.content.ContentValues;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.storage.Contract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class FeedsToContentValuesConverter implements IConverter<List<Feed>, ContentValues[]> {

    private int goalId;

    public FeedsToContentValuesConverter(int goalId) {
        this.goalId = goalId;
    }

    @Override
    public ContentValues[] convert(List<Feed> from) {
        if (from == null) return new ContentValues[0];

        ContentValues[] result = new ContentValues[from.size()];

        for (int idx = 0; idx < from.size(); idx++) {
            Feed feed = from.get(idx);

            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.FeedTable.FEED_ID, feed.getId());
            contentValues.put(Contract.FeedTable.AMOUNT, feed.getAmount());
            contentValues.put(Contract.FeedTable.TIMESTAMP, getTime(feed.getTimestamp()));
            contentValues.put(Contract.FeedTable.MESSAGE, feed.getMessage());
            contentValues.put(Contract.FeedTable.TYPE, feed.getType());
            contentValues.put(Contract.FeedTable.USER_ID, feed.getUserId());
            contentValues.put(Contract.FeedTable.GOALD_ID, goalId);
            contentValues.put(Contract.FeedTable.SAVINGS_RULE_ID, feed.getSavingsRuleId());

            result[idx] = contentValues;
        }

        return result;
    }

    private long getTime(final String timestamp) {
        long result = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            result = dateFormat.parse(timestamp).getTime();
        } catch (ParseException e) {
            Log.e(App.TAG, "Failed to obtain time", e);
        }
        return result;
    }
}
