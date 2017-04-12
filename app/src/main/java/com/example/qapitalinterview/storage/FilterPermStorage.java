package com.example.qapitalinterview.storage;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.qapitalinterview.App;
import com.example.qapitalinterview.model.SavingsRule;

import java.util.List;

/**
 * The intent of this class is provide permanent state storage for filters.
 * In memory storage isn't a good solution as activity might be recreated and state would be lost.
 * Implementation on database level creates overhead with join via multiple tables during
 * queries and clean step
 * */
public class FilterPermStorage {

    private static final String PREF_FILTER = "PREF_FILTER";

    private SharedPreferences preferences;

    public FilterPermStorage(Context context) {
        preferences = context.getSharedPreferences(PREF_FILTER, Context.MODE_PRIVATE);
    }

    public boolean isApplied(final int savingRuleId) {
        return preferences.getBoolean(String.valueOf(savingRuleId), false);
    }

    public void initFilters(List<SavingsRule> rules) {
        SharedPreferences.Editor editor = preferences.edit();
        for (SavingsRule rule : rules) {
            final String id = String.valueOf(rule.getId());
            editor.putBoolean(id, preferences.getBoolean(id, false));
        }
        editor.apply();
    }

    public static void clean(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILTER, Context.MODE_PRIVATE);
        preferences.edit().clear().apply();
    }

    public void changeState(final int savingRuleId, List<SavingsRule> rules) {
        final String id = String.valueOf(savingRuleId);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(id, !preferences.getBoolean(id, false));
        Log.d(App.TAG, "Reset filter state");
        for (SavingsRule rule : rules) {
            if (savingRuleId == rule.getId()) {
                continue;
            }
            editor.putBoolean(String.valueOf(rule.getId()), false);
        }
        editor.apply();
    }
}
