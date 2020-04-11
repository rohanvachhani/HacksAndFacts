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
import androidx.viewpager.widget.ViewPager;

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
                    }
                    endnow = android.os.SystemClock.uptimeMillis();
                    Log.v("r_log", "(LifeHacks activity): Execution time: " + (endnow - startnow) + " ms");

                    Log.v("r_log", "(Lifehacks activity): Life hacks string list size: " + listOfString.size());

                    //shuffle the list of string
                    if (listOfString.size() > 2) {
                        Collections.shuffle(listOfString, new Random(1 + new Random().nextInt(listOfString.size() - 2)));
                        Log.v("r_log", "(Lifehacks activity): list shuffled successfully.");
                    }


                    myAdapter = new MyAdapter(LifeHacksActivity.this, listOfString);
                    infiniteCycleViewPager.setAdapter(myAdapter);

                    infiniteCycleViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            showAd();
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                    //infiniteCycleViewPager.setOffscreenPageLimit(3);

                } else {
                    Log.v("r_log", "(LifeHacks activity) : in else no data snap shot is there!!!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                circularProgressBar.setVisibility(View.GONE);
                Log.v("r_log", "(LifeHacks activity) : in onCancelled method of Firebase DatabaseError");
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

        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial_test_ad_id));

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(getResources().getString(R.string.test_device_id)))
                .build();

        MobileAds.setRequestConfiguration(requestConfiguration);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }


    private void showAd() {
        int count = MainApplication.GLOBAL_ADS_COUNTER;
        if (count >= 2 && count % 12 == 0) {
            //load interstitial ad
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                Log.v("r_log", "(Lifehacks activity): The interstitial loaded successfully.");
            } else {
                Log.v("r_log", "(Lifehacks activity): The interstitial wasn't loaded yet.");
            }
        }
        MainApplication.GLOBAL_ADS_COUNTER++;
        Log.v("r_log", "(Lifehacks activity): Global Counter value: " + MainApplication.GLOBAL_ADS_COUNTER);
    }

    @Override
    public void onBackPressed() {
        Log.v("r_log", "(Lifehacks activity): back pressed successfully.");
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myAdapter = null;
        System.gc();
    }

}