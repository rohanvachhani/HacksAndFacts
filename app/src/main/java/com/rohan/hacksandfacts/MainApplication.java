package com.rohan.hacksandfacts;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class MainApplication extends Application {

    public static int GLOBAL_ADS_COUNTER = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }
}
