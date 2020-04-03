package com.rohan.hacksandfacts;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

public class FunFactsActivity extends AppCompatActivity {

    List<String> listOfString = new ArrayList<>();

    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;

    FirebaseDatabase firebaseDatabase;
    ImageView imageViewLogo, mainbackButton;

    private long startnow, endnow;
    CircularProgressBar circularProgressBar;

    static MyAdapter myAdapter;
    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_facts);

        circularProgressBar = findViewById(R.id.progress_bar);
        circularProgressBar.setVisibility(View.VISIBLE);
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

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        // Use bounce interpolator with amplitude 0.7 and frequency 30
        AnimationBounceInterpolator interpolator = new AnimationBounceInterpolator(0.7, 30);
        myAnim.setInterpolator(interpolator);
        mainbackButton.startAnimation(myAnim);

        DatabaseReference databaseReference = firebaseDatabase.getReference("fun_facts");
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

                    //shuffle the list of string
                    if (listOfString.size() > 2) {
                        Collections.shuffle(listOfString, new Random(1 + new Random().nextInt(listOfString.size() - 2)));
                    }
                    MyAdapter myAdapter = new MyAdapter(FunFactsActivity.this, listOfString);
                    infiniteCycleViewPager.setAdapter(myAdapter);
                    //infiniteCycleViewPager.setOffscreenPageLimit(3);

                } else {

                    // Toast.makeText(FunFactsActivity.this, "No data Loaded!", Toast.LENGTH_SHORT).show();
                    Log.i("mytag", "in else no data snap shot is there!!!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                circularProgressBar.setVisibility(View.GONE);
                // Toast.makeText(FunFactsActivity.this, "in On cancelled method", Toast.LENGTH_SHORT).show();
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
