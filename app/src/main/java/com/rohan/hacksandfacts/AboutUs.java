package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity implements View.OnClickListener {

    ImageView mainBackButton;
    TextView t1, t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        mainBackButton = findViewById(R.id.main_black_button);

        mainBackButton.setOnClickListener(this);

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        AnimationBounceInterpolator interpolator = new AnimationBounceInterpolator(0.7, 30);
        myAnim.setInterpolator(interpolator);


        t1 = findViewById(R.id.developer_one);
        t2 = findViewById(R.id.developer_two);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.fade_in_long);
        t1.startAnimation(a);
        t2.startAnimation(a);

        mainBackButton.startAnimation(myAnim);
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
