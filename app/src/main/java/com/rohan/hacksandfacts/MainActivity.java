package com.rohan.hacksandfacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;

    FirebaseDatabase firebaseDatabase;

    private long startnow, endnow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();

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

                    if (listOfString.size() == 0) {

                    }
                    infiniteCycleViewPager = findViewById(R.id.hicvp);
                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(), listOfString);
                    infiniteCycleViewPager.setAdapter(myAdapter);

                } else {
                    Toast.makeText(MainActivity.this, "No data Loaded!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





       /* final OnInfiniteCyclePageTransformListener onInfiniteCyclePageTransformListener = new OnInfiniteCyclePageTransformListener() {

            @Override
            public void onPreTransform(View page, float position) {

            }

            @Override
            public void onPostTransform(View page, float position) {

                int temp = infiniteCycleViewPager.getRealItem();

                if (current_real_item != temp) {
                    current_real_item = temp;
                    Log.i("ip", String.valueOf(current_real_item));
                }
                //rootView.setBackground(infiniteCycleViewPager.getChildAt(current_real_item).findViewById(R.id.imageView));

            }
        };*/

    }
}