package com.example.qapitalinterview.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.qapitalinterview.model.SavingsGoal;

public class GoalRepo {

    private static final String DATABASE_NAME = "data.db";
    private static final int DATABASE_VERSION = 1;


    protected SQLiteDatabase db;
    private final Context context;

    private LibOpenHelper dbHelper;

    public GoalRepo(Context context) {
        this.context = context;
        dbHelper = new LibOpenHelper(context);
    }

    public GoalRepo open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public GoalRepo openForRead() throws SQLException {
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public void close() {
        db.close();
    }

    public long addGoal(SavingsGoal goal) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.GoalTable.ID, goal.getId());
        contentValues.put(Contract.GoalTable.CURRENT_BALANCE, goal.getCurrentBalance());
        contentValues.put(Contract.GoalTable.GOAL_IMAGE_URL, goal.getGoalImageURL());
        contentValues.put(Contract.GoalTable.NAME, goal.getName());
        contentValues.put(Contract.GoalTable.STATUS, goal.getStatus());
        contentValues.put(Contract.GoalTable.TARGET_AMOUNT, goal.getTargetAmount());
        contentValues.put(Contract.GoalTable.USER_ID, goal.getUserId());
        return db.insert(Contract.getTableName(Contract.GoalTable.class), null, contentValues);
    }

    public long updateGoal(SavingsGoal goal) {
        String where = Contract.GoalTable.ID + " = " + goal.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.GoalTable.ID, goal.getId());
        contentValues.put(Contract.GoalTable.CURRENT_BALANCE, goal.getCurrentBalance());
        contentValues.put(Contract.GoalTable.GOAL_IMAGE_URL, goal.getGoalImageURL());
        contentValues.put(Contract.GoalTable.NAME, goal.getName());
        contentValues.put(Contract.GoalTable.STATUS, goal.getStatus());
        contentValues.put(Contract.GoalTable.TARGET_AMOUNT, goal.getTargetAmount());
        contentValues.put(Contract.GoalTable.USER_ID, goal.getUserId());
        return db.update(Contract.getTableName(Contract.GoalTable.class), contentValues, where, null);
    }

    public Cursor getAllGoals() {
        return db.query(Contract.getTableName(Contract.GoalTable.class), null, null, null, null, null, null);
    }

}
