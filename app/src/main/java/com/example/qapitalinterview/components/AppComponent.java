package com.example.qapitalinterview.components;


import android.app.Activity;

import com.example.qapitalinterview.api.ApiModule;
import com.example.qapitalinterview.model.Model;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface AppComponent {
    void inject(Activity activity);
    void inject(Model model);
}
