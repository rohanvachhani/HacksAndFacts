package com.rohan.hacksandfacts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {

    ImageButton mainBackButton;
    TextView t1, t2;
    private static int backPressCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        mainBackButton = findViewById(R.id.main_black_button);

        mainBackButton.setOnClickListener(this);


        t1 = findViewById(R.id.developer_one);
        t2 = findViewById(R.id.developer_two);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in_long);
        t1.startAnimation(a);
        t2.startAnimation(a);

        Animation b = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        b.setStartOffset(500);

        mainBackButton.startAnimation(b);
//        mainBackButton.startAnimation(myAnim);
    }


    @Override
    public void onClick(View v) {
        onBackPressed();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
