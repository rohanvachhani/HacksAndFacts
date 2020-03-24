package com.rohan.hacksandfacts;


import android.animation.Animator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<Integer> listOfImages;
    Context context;
    LayoutInflater layoutInflater;
    ConstraintLayout c1, c2;

    boolean isAnimationActive = false;

    ImageButton shareButton, backButton;
    boolean flag = true;


    public MyAdapter(List<Integer> listOfImages, Context context) {
        this.listOfImages = listOfImages;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = layoutInflater.from(context).inflate(R.layout.card_layout, container, false);
//        ImageView imageView = view.findViewById(R.id.image_view_1);
//        imageView.setImageResource(listOfImages.get(position));

        c1 = view.findViewById(R.id.first_side);
        shareButton = view.findViewById(R.id.share_button);
        c2 = view.findViewById(R.id.second_side);
        backButton = view.findViewById(R.id.back_button);

        c1.setVisibility(View.VISIBLE);
        c2.setVisibility(View.GONE);


        shareButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // Log.i("button", "share button clicked");

                Toast.makeText(context, "Share button clicked", Toast.LENGTH_SHORT).show();
                // anim_effect();

                c1.setVisibility(View.GONE);
                c2.setVisibility(View.VISIBLE);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // anim_effect();
                c1.setVisibility(View.VISIBLE);
                c2.setVisibility(View.GONE);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listOfImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    private void anim_effect() {

        int x = c1.getRight();
        int y = c1.getBottom();

        float pixelDensity = context.getResources().getDisplayMetrics().density;

        x = x - (int) ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(c1.getHeight(), c1.getWidth());

        if (flag) {

            Animator animator = ViewAnimationUtils.createCircularReveal(c2, x, y, 0, hypotenuse);
            animator.setDuration(700);

            c2.setVisibility(View.VISIBLE);
            animator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                    isAnimationActive = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    c1.setVisibility(View.GONE);
                    isAnimationActive = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // isAnimationActive = false;

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            if (!isAnimationActive) {
                animator.start();
                flag = false;
            }

        } else {

            Animator anim = ViewAnimationUtils.createCircularReveal(c2, x, y, hypotenuse, 0);
            anim.setDuration(400);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimationActive = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    c2.setVisibility(View.GONE);
                    isAnimationActive = false;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    // isAnimationActive = false;
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            if (!isAnimationActive) {
                anim.start();
                flag = true;
            }

        }
    }


}