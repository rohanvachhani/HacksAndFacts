package com.rohan.hacksandfacts;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<String> listOfString;
    Context context;
    LayoutInflater layoutInflater;
    boolean isAnimationActive = false;
    ConstraintLayout cl1, cl2;
    float pixelDensity;

    ImageButton shareMagic;
    ImageButton shareButton, backButton;
    ImageView card_image, back_image;

    static MediaPlayer mp;
    FrameLayout share_button_back;


    public MyAdapter(Context context, List<String> listOfString) {
        this.listOfString = listOfString;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        pixelDensity = context.getResources().getDisplayMetrics().density;
        mp = MediaPlayer.create(context, R.raw.bubble_pop);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        final View main_view = layoutInflater.from(context).inflate(R.layout.card_layout, container, false);
        final TextView tv = main_view.findViewById(R.id.text_view_1);
        card_image = main_view.findViewById(R.id.image_view_1);
        // back_image = main_view.findViewById(R.id.image_view_2);

        Typeface product_sans = Typeface.createFromAsset(context.getAssets(), "product_sans_black.ttf");
        tv.setText(listOfString.get(position));
        tv.setTypeface(product_sans);
        share_button_back = main_view.findViewById(R.id.whatsapp_button);
        // shareAnimation = main_view.findViewById(R.id.share_animation);

        shareButton = main_view.findViewById(R.id.share_button);

        backButton = main_view.findViewById(R.id.back_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!mp.isPlaying()) {
                    mp.start();

                }
                // Log.i("button", "share button clicked");

                cl1 = main_view.findViewById(R.id.first_side);
                cl2 = main_view.findViewById(R.id.second_side);

                anim_effect_share_button(cl1, cl2);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mp.isPlaying()) {
                    mp.start();

                }

                cl1 = main_view.findViewById(R.id.first_side);

                cl2 = main_view.findViewById(R.id.second_side);

                anim_effect_back_button(cl1, cl2);
                cl1.setVisibility(View.VISIBLE);
            }
        });

        share_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareBody = tv.getText().toString().trim();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hacks and Facts");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                context.startActivity(Intent.createChooser(sharingIntent, "abc"));

            }
        });

        container.addView(main_view);
        return main_view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return listOfString.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    private void anim_effect_share_button(final ConstraintLayout c1, final ConstraintLayout c2) {
        int x = c1.getRight();
        int y = c1.getBottom();

        //float pixelDensity = context.getResources().getDisplayMetrics().density;

        x = x - (int) ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(c1.getHeight(), c1.getWidth());


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

        }


    }

    private void anim_effect_back_button(final ConstraintLayout c1, final ConstraintLayout c2) {

        int x = c1.getRight();
        int y = c1.getBottom();

        //float pixelDensity = context.getResources().getDisplayMetrics().density;

        x = x - (int) ((28 * pixelDensity) + (16 * pixelDensity));

        int hypotenuse = (int) Math.hypot(c1.getHeight(), c1.getWidth());


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
        }
    }


}