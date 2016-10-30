package com.example.qapitalinterview.model;

import java.util.List;

import rx.Observable;

/**
 * Created by John on 10/30/2016.
 */

public interface IModel {

    Observable<SavingsGoals> getSavingGoals();
 }
