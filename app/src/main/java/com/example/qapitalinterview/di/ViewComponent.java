package com.example.qapitalinterview.di;


import com.example.qapitalinterview.view.MainActivity;
import com.example.qapitalinterview.view.fragment.GoalFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ViewModule.class})
public interface ViewComponent {
    void inject(MainActivity entity);
    void inject(GoalFragment goalFragment);
}
