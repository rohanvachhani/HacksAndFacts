package com.rohan.hacksandfacts;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity {


    ImageView logoImageView;
    List<String> listOfString;
    TextView titleTextView;

    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.postponeEnterTransition(this);
        setContentView(R.layout.activity_selection);


        infiniteCycleViewPager = findViewById(R.id.hicvp);
        logoImageView = findViewById(R.id.img_view);

        titleTextView = findViewById(R.id.title_text);
        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        titleTextView.startAnimation(a);

        listOfString = new ArrayList<>();

        listOfString.add("Life Hacks");

        listOfString.add("Fun Facts");
        listOfString.add("User Choice");

        listOfString.add("Submit Your Hacks or Facts");
        listOfString.add("About Us");

        AdapterForSelectionList pagerAdapter = new AdapterForSelectionList(this, listOfString);
        infiniteCycleViewPager.setAdapter(pagerAdapter);
        //infiniteCycleViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


}
