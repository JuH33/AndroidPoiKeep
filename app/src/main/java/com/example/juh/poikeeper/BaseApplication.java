package com.example.juh.poikeeper;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.mapbox.mapboxsdk.Mapbox;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Mapbox.getInstance(this, getString(R.string.token_access_mapboxgl));
    }
}
