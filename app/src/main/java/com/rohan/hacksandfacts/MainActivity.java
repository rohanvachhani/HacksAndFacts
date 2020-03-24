package com.rohan.hacksandfacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.gigamole.infinitecycleviewpager.OnInfiniteCyclePageTransformListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Integer> listOfImages = new ArrayList<>();
    ConstraintLayout rootView;
    int current_real_item = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.root_view);
        final HorizontalInfiniteCycleViewPager infiniteCycleViewPager =
                (HorizontalInfiniteCycleViewPager) findViewById(R.id.hicvp);

        listOfImages.add(R.drawable.velvet);
        listOfImages.add(R.drawable.velvet);
        listOfImages.add(R.drawable.velvet);
        listOfImages.add(R.drawable.velvet);
        listOfImages.add(R.drawable.velvet);
        listOfImages.add(R.drawable.velvet);
        listOfImages.add(R.drawable.velvet);

        final MyAdapter myAdapter = new MyAdapter(listOfImages, getBaseContext());
        infiniteCycleViewPager.setAdapter(myAdapter);

        final OnInfiniteCyclePageTransformListener onInfiniteCyclePageTransformListener = new OnInfiniteCyclePageTransformListener() {

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
        };

    }
}