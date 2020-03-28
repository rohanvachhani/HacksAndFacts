package com.rohan.hacksandfacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LifeHacksActivity extends AppCompatActivity {


    List<String> listOfString = new ArrayList<>();

    CardView c1;
    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;

    FirebaseDatabase firebaseDatabase;
    ImageView imageViewLogo;

    private long startnow, endnow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //overridePendingTransition(0, 0);
        //splash screen shown first and then this code will be executed

        //firebase code
        firebaseDatabase = FirebaseDatabase.getInstance();

        c1 = findViewById(R.id.oops_card);
        infiniteCycleViewPager = findViewById(R.id.hicvp);
        imageViewLogo = findViewById(R.id.img_view);
        DatabaseReference databaseReference = firebaseDatabase.getReference("life_hacks");
        databaseReference.keepSynced(true);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    startnow = android.os.SystemClock.uptimeMillis();

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        listOfString.add(ds.child("A").getValue().toString().trim());
                        Log.i("data", ds.child("A").getValue().toString());
                    }
                    endnow = android.os.SystemClock.uptimeMillis();
                    Log.d("MYTAG", "Execution time: " + (endnow - startnow) + " ms");

                    // firebaseCallBack.onCallBack(listOfStrings);

                    Log.i("final_data", "initial");
                    for (String s : listOfString) {
                        Log.i("final_data", s);
                    }
                    Toast.makeText(LifeHacksActivity.this, "size of the list: " + listOfString.size(), Toast.LENGTH_SHORT).show();

                    // listOfString.clear();
                    if (listOfString.size() == 0) {
                        c1.setVisibility(View.VISIBLE);
                        infiniteCycleViewPager.setVisibility(View.GONE);
                    } else {
                        c1.setVisibility(View.GONE);
                        infiniteCycleViewPager.setVisibility(View.VISIBLE);

                        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), listOfString);
                        infiniteCycleViewPager.setAdapter(myAdapter);
                    }
                } else {
                    Toast.makeText(LifeHacksActivity.this, "No data Loaded!", Toast.LENGTH_SHORT).show();
                    Log.i("mytag", "in else no data snap shot is there!!!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LifeHacksActivity.this, "in On cancelled method", Toast.LENGTH_SHORT).show();
            }
        });
    }
}