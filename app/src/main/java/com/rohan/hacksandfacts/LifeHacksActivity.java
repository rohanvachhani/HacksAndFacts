package com.rohan.hacksandfacts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;

public class LifeHacksActivity extends AppCompatActivity {


    List<String> listOfString = new ArrayList<>();

    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;

    FirebaseDatabase firebaseDatabase;
    ImageView imageViewLogo;
    ImageButton mainbackButton;

    private long startnow, endnow;

    CircularProgressBar circularProgressBar;
    TextView textViewTitle;
    static MyAdapter myAdapter;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgressBar = findViewById(R.id.progress_bar);
        circularProgressBar.setVisibility(View.VISIBLE);

        initilizeADs();

        //firebase code
        firebaseDatabase = FirebaseDatabase.getInstance();

        infiniteCycleViewPager = findViewById(R.id.hicvp);
        imageViewLogo = findViewById(R.id.img_view);
        mainbackButton = findViewById(R.id.main_black_button);
        mainbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewTitle = findViewById(R.id.title);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in_long);
        textViewTitle.startAnimation(a);

        Animation b = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        b.setStartOffset(500);

        mainbackButton.startAnimation(b);

//        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//        // Use bounce interpolator with amplitude 0.7 and frequency 30
//        AnimationBounceInterpolator interpolator = new AnimationBounceInterpolator(0.7, 30);
//        myAnim.setInterpolator(interpolator);
//        mainbackButton.startAnimation(myAnim);

        //mp = MediaPlayer.create(LifeHacksActivity.this, R.raw.book_flip);
        DatabaseReference databaseReference = firebaseDatabase.getReference("life_hacks");
        databaseReference.keepSynced(true);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                circularProgressBar.setVisibility(View.GONE);

                if (dataSnapshot.exists()) {
                    startnow = android.os.SystemClock.uptimeMillis();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        listOfString.add(ds.child("A").getValue().toString().trim());
                        //Log.i("data", ds.child("A").getValue().toString());
                    }
                    endnow = android.os.SystemClock.uptimeMillis();
                    Log.d("MYTAG", "Execution time: " + (endnow - startnow) + " ms");


                    //shuffle the list of string
                    if (listOfString.size() > 2) {
                        Collections.shuffle(listOfString, new Random(1 + new Random().nextInt(listOfString.size() - 2)));
                    }

                    myAdapter = new MyAdapter(LifeHacksActivity.this, listOfString);
                    infiniteCycleViewPager.setAdapter(myAdapter);

                    //infiniteCycleViewPager.setOffscreenPageLimit(3);

                } else {
                    //Toast.makeText(LifeHacksActivity.this, "No data Loaded!", Toast.LENGTH_SHORT).show();
                    Log.i("mytag", "in else no data snap shot is there!!!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                circularProgressBar.setVisibility(View.GONE);
                //Toast.makeText(LifeHacksActivity.this, "in On cancelled method", Toast.LENGTH_SHORT).show();
            }
        });
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
        int count = MainApplication.GLOBAL_ADS_COUNTER;
        if (count >= 1 && count % 2 == 0) {
            //load interstitial ad
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        }

        MainApplication.GLOBAL_ADS_COUNTER++;
        Log.v("counter", String.valueOf(MainApplication.GLOBAL_ADS_COUNTER));
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAdapter = null;
        Runtime.getRuntime().gc();
    }
}