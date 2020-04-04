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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
    // static MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularProgressBar = findViewById(R.id.progress_bar);
        circularProgressBar.setVisibility(View.VISIBLE);
        //overridePendingTransition(0, 0);
        //splash screen shown first and then this code will be executed

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

    @Override
    public void onBackPressed() {
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