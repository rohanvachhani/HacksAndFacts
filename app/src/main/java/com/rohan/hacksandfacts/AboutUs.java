package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

        if (backPressCount >= 1 && backPressCount % 2 == 0) {
            //load interstitial ad

            //load interstitial ad again on after ad load method (Override)
        }
        backPressCount++;
        super.onBackPressed();
        finish();

    }
}
