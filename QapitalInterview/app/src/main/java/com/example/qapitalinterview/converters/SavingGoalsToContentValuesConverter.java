package com.example.qapitalinterview.converters;


import android.content.ContentValues;

import com.example.qapitalinterview.model.SavingsGoal;
import com.example.qapitalinterview.model.SavingsGoals;
import com.example.qapitalinterview.storage.Contract;

import java.util.List;

public class SavingGoalsToContentValuesConverter implements IConverter<List<SavingsGoal>, ContentValues[]> {

    @Override
    public ContentValues[] convert(List<SavingsGoal> from) {
        if (from == null) return new ContentValues[0];

        ContentValues[] result = new ContentValues[from.size()];
        for (int idx = 0; idx < from.size(); idx++) {
            SavingsGoal goal = from.get(idx);
            result[idx] = asContentValues(goal);
        }
        return result;
    }

    private ContentValues asContentValues(SavingsGoal data) {
        ContentValues result = new ContentValues();
        result.put(Contract.GoalTable.USER_ID, data.getUserId());
        result.put(Contract.GoalTable.TARGET_AMOUNT, data.getTargetAmount());
        result.put(Contract.GoalTable.STATUS, data.getStatus());
        result.put(Contract.GoalTable.NAME, data.getName());
        result.put(Contract.GoalTable.CURRENT_BALANCE, data.getCurrentBalance());
        result.put(Contract.GoalTable.GOAL_IMAGE_URL, data.getGoalImageURL());
        result.put(Contract.GoalTable.ID, data.getId());
        return result;
    }
}
