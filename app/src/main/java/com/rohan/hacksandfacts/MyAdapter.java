package com.rohan.hacksandfacts;


import android.animation.Animator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    ImageButton shareButton, backButton;
    boolean flag = true;


    public MyAdapter(Context context, List<String> listOfString) {
        this.listOfString = listOfString;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        pixelDensity = context.getResources().getDisplayMetrics().density;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        final View main_view = layoutInflater.from(context).inflate(R.layout.card_layout, container, false);
        TextView tv = main_view.findViewById(R.id.text_view_1);
        tv.setText(listOfString.get(position));
        // ImageView imageView1 = view.findViewById(R.id.image_view_1);
        // imageView1.setImageResource(listOfImages.get(position));

        // c1 = (ConstraintLayout) view.findViewById(R.id.first_side);
        shareButton = main_view.findViewById(R.id.share_button);
        //c2 = (ConstraintLayout) view.findViewById(R.id.second_side);
        backButton = main_view.findViewById(R.id.back_button);


//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView tv1 = v.findViewById(R.id.text_view_1);
//                c1 = (ConstraintLayout) v.findViewById(R.id.first_side);
//                shareButton = v.findViewById(R.id.share_button);
//                c2 = (ConstraintLayout) v.findViewById(R.id.second_side);
//                backButton = v.findViewById(R.id.back_button);
//                c1.setVisibility(View.GONE);
//                c2.setVisibility(View.VISIBLE);
//                Toast.makeText(context, "Card " + tv1.getText().toString() + " is clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Log.i("button", "share button clicked");

                cl1 = (ConstraintLayout) main_view.findViewById(R.id.first_side);
                //shareButton = v.findViewById(R.id.share_button);
                cl2 = (ConstraintLayout) main_view.findViewById(R.id.second_side);
                //backButton = v.findViewById(R.id.back_button);

                anim_effect_share_button(cl1, cl2);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // anim_effect();
                cl1 = (ConstraintLayout) main_view.findViewById(R.id.first_side);
                //shareButton = v.findViewById(R.id.share_button);
                cl2 = (ConstraintLayout) main_view.findViewById(R.id.second_side);
                //backButton = v.findViewById(R.id.back_button);
                anim_effect_back_button(cl1, cl2);
                cl1.setVisibility(View.VISIBLE);
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