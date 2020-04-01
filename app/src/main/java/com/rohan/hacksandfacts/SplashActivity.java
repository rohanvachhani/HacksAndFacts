package com.rohan.hacksandfacts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {


    ImageView logoImageView;

    Animation animationFadeIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logoImageView = findViewById(R.id.img_view);
        animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        logoImageView.startAnimation(animationFadeIn);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, SelectionActivity.class);

                startActivity(i);
                finish();
            }
        }, 2000);
    }
}
