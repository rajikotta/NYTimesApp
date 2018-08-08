package com.nytimes.febyelsa.nytimesapp;

import android.app.Application;

import com.nytimes.febyelsa.nytimesapp.di.AppComponent;
import com.nytimes.febyelsa.nytimesapp.di.AppModule;
import com.nytimes.febyelsa.nytimesapp.di.DaggerAppComponent;

public class NYTimesApplication extends Application {

    AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this) .build();
        appComponent.inject(this);
    }
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
