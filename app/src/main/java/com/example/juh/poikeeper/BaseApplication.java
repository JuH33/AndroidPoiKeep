package com.example.juh.poikeeper;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
