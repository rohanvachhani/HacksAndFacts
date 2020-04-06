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
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initilizeADs();

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

    private void initilizeADs() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);

        //Test interstitial Ad unit ID:  ca-app-pub-3940256099942544/1033173712
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList("B6B92AB274C327E0B291D641F4D683BD"))
                .build();

        MobileAds.setRequestConfiguration(requestConfiguration);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void showAd() {
        int count = MainApplication.GLOBAL_ADS_COUNTER;
        if (count >= 10 && count % 5 == 0) {
            //load interstitial ad
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }

        MainApplication.GLOBAL_ADS_COUNTER++;
    }
}
