package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SubmitSuggestion extends AppCompatActivity implements View.OnClickListener {

    ImageView instagramIcon, gmailIcon, mainBackButton, mainTextImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_suggestion);

        instagramIcon = findViewById(R.id.instagram_icon);
        gmailIcon = findViewById(R.id.gmail_icon);
        mainBackButton = findViewById(R.id.main_black_button);
        mainTextImageViews = findViewById(R.id.main_image_view);

        instagramIcon.setOnClickListener(this);
        gmailIcon.setOnClickListener(this);
        mainBackButton.setOnClickListener(this);

//        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
//        // Use bounce interpolator with amplitude 0.2 and frequency 20
//        AnimationBounceInterpolator interpolator = new AnimationBounceInterpolator(0.7, 30);
//        myAnim.setInterpolator(interpolator);
//        mainBackButton.startAnimation(myAnim);

        Animation b = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        b.setStartOffset(1000);

        mainBackButton.startAnimation(b);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

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
        }
    }
}
