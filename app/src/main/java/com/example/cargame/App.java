package com.example.cargame;

import android.app.Application;

import com.example.cargame.Utilities.SharedPreferencesManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesManager.init(this);
    }
}
