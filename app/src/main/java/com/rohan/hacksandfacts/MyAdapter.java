package com.rohan.hacksandfacts;


import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;
import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<String> listOfString;
    Context context;
    LayoutInflater layoutInflater;
    boolean isAnimationActive = false;
    ConstraintLayout cl1, cl2;
    float pixelDensity;

    ImageButton shareButton, backButton;
    ImageView card_image;
    FrameLayout share_button_back;
    private static InterstitialAd mInterstitialAd;


    public MyAdapter(Context context, List<String> listOfString) {
        this.listOfString = listOfString;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        pixelDensity = context.getResources().getDisplayMetrics().density;
        initilizeADs();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        final View main_view = layoutInflater.from(context).inflate(R.layout.card_layout, container, false);
        final TextView tv = main_view.findViewById(R.id.text_view_1);
        card_image = main_view.findViewById(R.id.image_view_1);

        Typeface product_sans = Typeface.createFromAsset(context.getAssets(), "product_sans_black.ttf");
        tv.setText(listOfString.get(position));
        tv.setTypeface(product_sans);
        share_button_back = main_view.findViewById(R.id.whatsapp_button);

        shareButton = main_view.findViewById(R.id.share_button);

        backButton = main_view.findViewById(R.id.back_button);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAd();
                cl1 = main_view.findViewById(R.id.first_side);
                cl2 = main_view.findViewById(R.id.second_side);

                anim_effect_share_button(cl1, cl2);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


    private void initilizeADs() {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(context);

        mInterstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial_test_ad_id));

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(context.getResources().getString(R.string.test_device_id)))
                .build();

        MobileAds.setRequestConfiguration(requestConfiguration);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                //Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
    }

    public static void showAd() {
        int count = MainApplication.GLOBAL_ADS_COUNTER_SHARE_BUTTON;
        if (count >= 1 && count % 3 == 0) {
            //load interstitial ad
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                Log.v("r_log", "(MyAdapater(for all cards's share button) class: The interstitial loaded successfully.");
            } else {
                Log.v("r_log", "(MyAdapater(for all cards's share button) class: The interstitial wasn't loaded yet.");
            }
        }

        MainApplication.GLOBAL_ADS_COUNTER_SHARE_BUTTON++;
        Log.v("r_log", "MyAdapater(for all cards's share button) class: share button ad Global Counter value: " + MainApplication.GLOBAL_ADS_COUNTER_SHARE_BUTTON);
    }


}