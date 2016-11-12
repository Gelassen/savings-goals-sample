package com.example.qapitalinterview.model.datasource;


import android.database.Cursor;

public interface IDataSource<I,T> {
    int getAmount();
    T getItemForPosition(int position);
    void setModel(I data);
}
