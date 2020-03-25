package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.OnInfiniteCyclePageTransformListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> listOfImages = new ArrayList<>();
    HorizontalInfiniteCycleViewPager infiniteCycleViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOfImages.add("1");
        listOfImages.add("2");
        listOfImages.add("3");


        infiniteCycleViewPager = findViewById(R.id.hicvp);
        MyAdapter myAdapter = new MyAdapter(this, listOfImages);
        infiniteCycleViewPager.setAdapter(myAdapter);

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