package com.example.qapitalinterview.converters;

import android.database.Cursor;

import com.example.qapitalinterview.model.SavingsGoal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 10/30/2016.
 */

public class CursorToSavingGoalsListConverter implements IConverter<Cursor, List<SavingsGoal>> {

    private CursorToSavingGoalConverter converter;

    public CursorToSavingGoalsListConverter() {
        converter = new CursorToSavingGoalConverter();
    }

    @Override
    public List<SavingsGoal> convert(Cursor from) {
        if (from == null) return new ArrayList<>();

        List<SavingsGoal> result = new ArrayList<>();

        while (from.moveToNext()) {
            SavingsGoal savingsGoal = converter.convert(from);

            result.add(savingsGoal);
        }
        return result;
    }

}
