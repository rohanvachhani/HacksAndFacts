package com.rohan.hacksandfacts;

import android.app.Application;
import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;


public class MainApplication extends Application {

    public static int GLOBAL_ADS_COUNTER = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Log.v("r_log", "(Mainapplication class): Oncreate method: firebase persistence made true.");
    }
}
