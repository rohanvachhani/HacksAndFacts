package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.ArrayList;
import java.util.List;

public class SelectionActivity extends AppCompatActivity {


    ImageView logoImageView;
    List<String> listOfString;

    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        overridePendingTransition(0, 0);

        logoImageView = findViewById(R.id.img_view);
        listOfString = new ArrayList<>();

        listOfString.add("Life Hacks");
        listOfString.add("Fun Facts");
        listOfString.add("User's Choice");

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logoImageView.startAnimation(anim1);

        infiniteCycleViewPager = findViewById(R.id.hicvp);

        AdapterForSelectionList myAdapter = new AdapterForSelectionList(getApplicationContext(), listOfString);
        infiniteCycleViewPager.setAdapter(myAdapter);




    }
}
