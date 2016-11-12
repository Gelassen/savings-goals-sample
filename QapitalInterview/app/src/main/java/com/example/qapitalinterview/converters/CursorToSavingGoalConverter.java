package com.example.qapitalinterview.converters;

import android.database.Cursor;

import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.storage.Contract;

/**
 * Created by John on 10/30/2016.
 */

public class CursorToSavingGoalConverter implements IConverter<Cursor, SavingsGoal> {

    private static final int NOT_INITIALIZED = -1;

    private int idIdx = NOT_INITIALIZED;
    private int currentBalanceIdx = NOT_INITIALIZED;
    private int goalImageUrlIdx = NOT_INITIALIZED;
    private int nameIdx = NOT_INITIALIZED;
    private int statusIdx = NOT_INITIALIZED;
    private int targetAmountIdx = NOT_INITIALIZED;
    private int userIdIdx = NOT_INITIALIZED;

    @Override
    public SavingsGoal convert(Cursor from) {
        if (from == null) return new SavingsGoal();

        initIdx(from);

        SavingsGoal savingsGoal = new SavingsGoal();
        savingsGoal.setId(from.getInt(idIdx));
        savingsGoal.setName(from.getString(nameIdx));
        savingsGoal.setStatus(from.getString(statusIdx));
        savingsGoal.setTargetAmount(from.getFloat(targetAmountIdx));
        savingsGoal.setUserId(from.getInt(userIdIdx));
        savingsGoal.setCurrentBalance(from.getInt(currentBalanceIdx));
        savingsGoal.setGoalImageURL(from.getString(goalImageUrlIdx));

        return savingsGoal;
    }

    public void initIdx(Cursor cursor) {
        if (idIdx != NOT_INITIALIZED) return;

        idIdx = cursor.getColumnIndex(Contract.GoalTable.ID);
        currentBalanceIdx = cursor.getColumnIndex(Contract.GoalTable.CURRENT_BALANCE);
        goalImageUrlIdx = cursor.getColumnIndex(Contract.GoalTable.GOAL_IMAGE_URL);
        nameIdx = cursor.getColumnIndex(Contract.GoalTable.NAME);
        statusIdx = cursor.getColumnIndex(Contract.GoalTable.STATUS);
        targetAmountIdx = cursor.getColumnIndex(Contract.GoalTable.TARGET_AMOUNT);
        userIdIdx = cursor.getColumnIndex(Contract.GoalTable.USER_ID);
    }
}
