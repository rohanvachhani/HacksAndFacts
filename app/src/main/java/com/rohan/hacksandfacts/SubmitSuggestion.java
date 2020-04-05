package com.rohan.hacksandfacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;

public class SubmitSuggestion extends FragmentActivity implements View.OnClickListener {

    ImageView instagramIcon, gmailIcon, mainTextImageViews, info_button;
    ImageButton mainBackButton;
    private static int backPressCount = 0;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_suggestion);

        initilizeADs();

        instagramIcon = findViewById(R.id.instagram_icon);
        gmailIcon = findViewById(R.id.gmail_icon);
        mainBackButton = findViewById(R.id.main_black_button);
        mainTextImageViews = findViewById(R.id.main_image_view);
        info_button = findViewById(R.id.info_button);

        instagramIcon.setOnClickListener(this);
        gmailIcon.setOnClickListener(this);
        mainBackButton.setOnClickListener(this);
        info_button.setOnClickListener(this);

        Animation b = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        b.setStartOffset(500);
        mainBackButton.startAnimation(b);
    }

    @Override
    public void onBackPressed() {

        if (MainApplication.GLOBAL_ADS_COUNTER >= 1 && MainApplication.GLOBAL_ADS_COUNTER % 2 == 0) {
            //load interstitial ad

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
            //load interstitial ad again on after ad load method (Override)
        }
        MainApplication.GLOBAL_ADS_COUNTER++;
        Log.v("counter", String.valueOf(MainApplication.GLOBAL_ADS_COUNTER));
        
        super.onBackPressed();
        finish();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.instagram_icon:
                Uri uri = Uri.parse("http://instagram.com/_u/hacks_and_facts/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                likeIng.setPackage("com.instagram.android");
                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/hacks_and_facts/")));
                }
                break;
            case R.id.gmail_icon:
                String to = "lifehacksandfunfacts@gmail.com";
                String subject = "Hey, how about this cool Hack/Fact?";

                String mailTo = "mailto:" + to +
                        "?&subject=" + Uri.encode(subject);
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);
                emailIntent.setData(Uri.parse(mailTo));
                startActivity(emailIntent);
                break;
            case R.id.main_black_button:
                onBackPressed();
                break;
            case R.id.info_button:
                CustomDialogFragment customDialogFragment = new CustomDialogFragment();
                customDialogFragment.show(getSupportFragmentManager(), "customDialogFragment");
                break;
        }
    }
}
