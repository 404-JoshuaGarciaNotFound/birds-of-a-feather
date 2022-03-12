package com.example.myapplication;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Application app;
    public static Context getContext() {
        return app.getApplicationContext();
    }

    @Override
    public void onCreate() {
            super.onCreate();
            app = this;
    }
}
