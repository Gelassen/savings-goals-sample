package com.example.qapitalinterview.storage;


import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    public final static String AUTHORITY = "com.example.qapitalinterview";
    public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    private final static String _ID = BaseColumns._ID;
    private final static String _PK_AUTOINCREMENT = "INTEGER PRIMARY KEY AUTOINCREMENT";
    private final static String _TYPE_INTEGER = "INTEGER";

    public final static Uri contentUri(final Class klass) {
        Uri.Builder builder = CONTENT_URI.buildUpon();
        builder.appendPath(klass.getSimpleName());
        return builder.build();
    }

    /* reflection based schema management */
    /*
     * Copyright (C) 2012 Random Android Code Snippets
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *      http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    /*package*/ static interface SqlEntity { };

    /** Simple marker class */
    public static class Table implements SqlEntity { }

    /*package*/ static class View implements SqlEntity { }

    /*package*/ public static class Join implements SqlEntity {
        final static String getJoinTable(Class<? extends Join> join) {
            try {
                return (String) join.getField("JOIN").get(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /*package*/ final static String getTableName(final Class<? extends SqlEntity> table) {
        return table.getSimpleName();
    }

    public final static class GoalTable extends Table {
        public final static String ID = _ID;
        final static String _SQL_ID_TYPE = _PK_AUTOINCREMENT;
        public final static String GOAL_IMAGE_URL = "GOAL_IMAGE_URL";
        public final static String USER_ID = "USER_ID";
        public final static String TARGET_AMOUNT = "TARGET_AMOUNT";
        public final static String CURRENT_BALANCE = "CURRENT_BALANCE";
        public final static String STATUS = "STATUS";
        public final static String NAME = "NAME";
    }

    public final static class FeedTable extends Table {
        public final static String ID = _ID;
        final static String _SQL_ID_TYPE = _PK_AUTOINCREMENT;
        public final static String FEED_ID = "FEED_ID";
        public final static String TYPE = "TYPE";
        public final static String TIMESTAMP = "TIMESTAMP";
        public final static String MESSAGE = "MESSAGE";
        public final static String AMOUNT = "AMOUNT";
        public final static String USER_ID = "USER_ID";
        public final static String GOALD_ID = "GOAL_ID";
        public final static String SAVINGS_RULE_ID = "SAVINGS_RULE_ID";
    }

    public final static class SavingsRuleTable extends Table {
        public final static String ID = _ID;
        final static String _SQL_ID_TYPE = _PK_AUTOINCREMENT;
        public final static String TYPE = "TYPE";
        public final static String AMOUNT = "AMOUNT";
    }

}
