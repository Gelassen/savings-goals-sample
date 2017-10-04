package com.example.qapitalinterview.di;


import com.example.qapitalinterview.model.Model;
import com.example.qapitalinterview.presenter.SavingsGoalPresenter;

public interface IComponent {
    void inject(SavingsGoalPresenter presenter);

    void inject(Model model);
}
