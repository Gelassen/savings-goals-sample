package com.example.qapitalinterview.converters;


import android.database.Cursor;

import com.example.qapitalinterview.model.Feed;
import com.example.qapitalinterview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

public class CursorToFeedsConverter implements IConverter<Cursor, List<Feed>> {

    private static final int NOT_INIT = -1;

    private int feedIdx = NOT_INIT;
    private int typeIdx = NOT_INIT;
    private int timestampIdx = NOT_INIT;
    private int messageIdx = NOT_INIT;
    private int amountIdx = NOT_INIT;
    private int userIdIdx = NOT_INIT;
    private int goalIdx = NOT_INIT;

    @Override
    public List<Feed> convert(Cursor cursor) {
        if (cursor == null) return new ArrayList<>();

        List<Feed> result = new ArrayList<>();
        init(cursor);

        while (cursor.moveToNext()) {
            Feed feed = new Feed();
            feed.setId(cursor.getString(feedIdx));
            feed.setType(cursor.getString(typeIdx));
            feed.setTime(cursor.getLong(timestampIdx));
            feed.setMessage(cursor.getString(messageIdx));
            feed.setAmount(cursor.getDouble(amountIdx));
            feed.setUserId(cursor.getInt(userIdIdx));
            feed.setGoalId(cursor.getInt(goalIdx));

            result.add(feed);
        }

        return result;
    }

    private void init(Cursor cursor) {
        if (feedIdx != NOT_INIT) return;

        feedIdx = cursor.getColumnIndex(Contract.FeedTable.FEED_ID);
        typeIdx = cursor.getColumnIndex(Contract.FeedTable.TYPE);
        timestampIdx = cursor.getColumnIndex(Contract.FeedTable.TIMESTAMP);
        messageIdx = cursor.getColumnIndex(Contract.FeedTable.MESSAGE);
        amountIdx = cursor.getColumnIndex(Contract.FeedTable.AMOUNT);
        userIdIdx = cursor.getColumnIndex(Contract.FeedTable.USER_ID);
        goalIdx = cursor.getColumnIndex(Contract.FeedTable.GOALD_ID);
    }
}
