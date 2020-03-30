package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.VerticalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity {


    ImageView logoImageView;
    List<String> listOfString;

   // VerticalInfiniteCycleViewPager infiniteCycleViewPager;

    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.postponeEnterTransition(this);
        setContentView(R.layout.activity_selection);
        // overridePendingTransition(0, 0);

        infiniteCycleViewPager = findViewById(R.id.hicvp);
        logoImageView = findViewById(R.id.img_view);

        listOfString = new ArrayList<>();

        listOfString.add("Life Hacks");
        listOfString.add("User Choice");
        listOfString.add("Fun Facts");
        listOfString.add("User Choice");
        listOfString.add("Fun Facts");
        listOfString.add("User Choice");

        AdapterForSelectionList pagerAdapter = new AdapterForSelectionList(this, listOfString);
        infiniteCycleViewPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}
