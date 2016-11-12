package com.example.qapitalinterview.model.datasource;


import android.database.Cursor;

import com.example.qapitalinterview.converters.CursorToSavingGoalConverter;
import com.example.qapitalinterview.model.SavingsGoal;

public class CursorDataSource implements IDataSource<Cursor, SavingsGoal> {

    private CursorToSavingGoalConverter converter;
    private Cursor cursor;

    public CursorDataSource() {
        converter = new CursorToSavingGoalConverter();
    }

    public void updateModel(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public int getAmount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    @Override
    public SavingsGoal getItemForPosition(int position) {
        cursor.moveToPosition(position);
        return converter.convert(cursor);
    }

    @Override
    public void setModel(Cursor data) {
        this.cursor = data;
    }

}
