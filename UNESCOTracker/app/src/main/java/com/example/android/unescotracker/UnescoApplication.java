package com.example.android.unescotracker;

import android.app.Application;
import android.content.Context;

public class UnescoApplication extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        UnescoApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return UnescoApplication.context;
    }
}