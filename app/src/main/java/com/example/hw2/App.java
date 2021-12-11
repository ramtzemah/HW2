package com.example.hw2;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MSPV.initHelper(this);
    }
}
