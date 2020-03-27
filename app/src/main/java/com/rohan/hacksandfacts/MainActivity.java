package com.rohan.hacksandfacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    List<String> listOfString = new ArrayList<>();

    CardView c1;
    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;

    FirebaseDatabase firebaseDatabase;

    private long startnow, endnow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //splash screen shown first and then this code will be executed

        //firebase code
        firebaseDatabase = FirebaseDatabase.getInstance();

        c1 = findViewById(R.id.oops_card);
        infiniteCycleViewPager = findViewById(R.id.hicvp);

        DatabaseReference databaseReference = firebaseDatabase.getReference();
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
                    Toast.makeText(MainActivity.this, "size of the list: " + listOfString.size(), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(MainActivity.this, "No data Loaded!", Toast.LENGTH_SHORT).show();
                    Log.i("mytag", "in else no data snap shot is there!!!!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "in On cancelled method", Toast.LENGTH_SHORT).show();
            }
        });
    }
}