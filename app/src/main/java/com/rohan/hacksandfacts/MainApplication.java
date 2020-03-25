package com.rohan.hacksandfacts;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class MainApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
