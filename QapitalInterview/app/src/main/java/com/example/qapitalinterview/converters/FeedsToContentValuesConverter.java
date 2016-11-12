package com.example.qapitalinterview.converters;


import android.content.ContentValues;

import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.storage.Contract;

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
            contentValues.put(Contract.FeedTable.TIMESTAMP, feed.getTimestamp());
            contentValues.put(Contract.FeedTable.MESSAGE, feed.getMessage());
            contentValues.put(Contract.FeedTable.TYPE, feed.getType());
            contentValues.put(Contract.FeedTable.USER_ID, feed.getUserId());
            contentValues.put(Contract.FeedTable.GOALD_ID, goalId);

            result[idx] = contentValues;
        }

        return result;
    }
}
