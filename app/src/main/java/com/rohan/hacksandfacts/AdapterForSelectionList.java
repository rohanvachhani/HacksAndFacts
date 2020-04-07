package com.rohan.hacksandfacts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

public class AdapterForSelectionList extends PagerAdapter {
    private final List<String> listOfString;
    private final Context context;
    private static InterstitialAd mInterstitialAd;
    private final LayoutInflater layoutInflater;


    static Intent i = null;

    public AdapterForSelectionList(Context applicationContext, List<String> listOfString) {
        this.listOfString = listOfString;
        this.context = applicationContext;
        this.layoutInflater = LayoutInflater.from(context);
        initilizeADs();
    }

    @Override
    public int getCount() {
        return listOfString.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, int position) {
        final View main_view = layoutInflater.from(context).inflate(R.layout.card_view_small, container, false);
        final TextView tv = main_view.findViewById(R.id.text_view_1);
        Typeface product_sans = Typeface.createFromAsset(context.getAssets(), "product_sans_black.ttf");
        tv.setText(listOfString.get(position));
        tv.setTypeface(product_sans);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "card text: " + tv.getText(), Toast.LENGTH_SHORT).show();

                switch (tv.getText().toString().trim().toLowerCase()) {
                    case "life hacks":
                        i = new Intent(v.getContext(), LifeHacksActivity.class);
                        break;
                    case "fun facts":
                        i = new Intent(v.getContext(), FunFactsActivity.class);
                        break;
                    case "user choice":
                        i = new Intent(v.getContext(), ByUsers.class);
                        break;
                    case "submit your hacks or facts":
                        showAd();
                        i = new Intent(v.getContext(), SubmitSuggestion.class);
                        break;
                    case "about us":
                        i = new Intent(v.getContext(), AboutUs.class);
                        break;
                }
                v.getContext().startActivity(i);
            }
        });

        container.addView(main_view);
        return main_view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    private void initilizeADs() {
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(context);

        mInterstitialAd.setAdUnitId(String.valueOf(R.string.interstitial_test_ad_id));

        RequestConfiguration requestConfiguration
                = new RequestConfiguration.Builder()
                .setTestDeviceIds(Arrays.asList(String.valueOf(R.string.test_device_id)))
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
        int count = MainApplication.GLOBAL_ADS_COUNTER;
        if (count >= 1 && count % 2 == 0) {
            //load interstitial ad
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                Log.v("r_log", "(Selection List activity adapter class): The interstitial loaded successfully.");
            } else {
                Log.v("r_log", "(Selection List activity adapter class): The interstitial wasn't loaded yet.");
            }
        }

        MainApplication.GLOBAL_ADS_COUNTER++;
        Log.v("r_log", "(Selection List activity adapter class): Global Counter value: " + MainApplication.GLOBAL_ADS_COUNTER);
    }


}
