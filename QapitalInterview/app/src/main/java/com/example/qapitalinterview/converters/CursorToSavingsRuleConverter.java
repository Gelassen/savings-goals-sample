package com.example.qapitalinterview.converters;


import android.database.Cursor;

import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.storage.Contract;

import java.util.ArrayList;
import java.util.List;

public class CursorToSavingsRuleConverter implements IConverter<Cursor, List<SavingsRule>> {

    private static final int NOT_INIT = -1;

    private int idIdx = NOT_INIT;
    private int typeIdx = NOT_INIT;
    private int amountIdx = NOT_INIT;

    @Override
    public List<SavingsRule> convert(Cursor cursor) {
        if (cursor == null) return new ArrayList<>();

        List<SavingsRule> result = new ArrayList<>();

        initIndexes(cursor);

        while (cursor.moveToNext()) {
            SavingsRule savingsRule = new SavingsRule();
            savingsRule.setId(cursor.getInt(idIdx));
            savingsRule.setAmount(cursor.getInt(amountIdx));
            savingsRule.setType(cursor.getString(typeIdx));
            result.add(savingsRule);
        }

        return result;
    }

    private void initIndexes(Cursor cursor) {
        if (idIdx != NOT_INIT) return;

        idIdx = cursor.getColumnIndex(Contract.SavingsRuleTable.ID);
        typeIdx = cursor.getColumnIndex(Contract.SavingsRuleTable.TYPE);
        amountIdx = cursor.getColumnIndex(Contract.SavingsRuleTable.AMOUNT);
    }
}
