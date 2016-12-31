package com.example.qapitalinterview.converters;


import android.content.ContentValues;

import com.example.qapitalinterview.model.SavingRules;
import com.example.qapitalinterview.model.SavingsRule;
import com.example.qapitalinterview.storage.Contract;

import java.util.List;

public class SavingRulesToContentValuesConverter implements IConverter<List<SavingsRule>, ContentValues[]> {

    @Override
    public ContentValues[] convert(List<SavingsRule> from) {
        if (from == null) return new ContentValues[0];

        ContentValues[] result = new ContentValues[from.size()];

        for (int idx = 0; idx < from.size(); idx++) {
            SavingsRule rule = from.get(idx);

            ContentValues values = new ContentValues();
            values.put(Contract.SavingsRuleTable.ID, rule.getId());
            values.put(Contract.SavingsRuleTable.AMOUNT, rule.getAmount());
            values.put(Contract.SavingsRuleTable.TYPE, rule.getType());
            result[idx] = values;
        }

        return result;
    }
}
