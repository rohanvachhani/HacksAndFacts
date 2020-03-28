package com.rohan.hacksandfacts;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class AdapterForSelectionList extends PagerAdapter {
    private final List<String> listOfString;
    private final Context context;
    private final LayoutInflater layoutInflater;
    static Animation animationFadeIn;
    static Intent i = null;
    ImageView iV;

    public AdapterForSelectionList(Activity applicationContext, List<String> listOfString) {
        this.listOfString = listOfString;
        this.context = applicationContext;
        this.layoutInflater = LayoutInflater.from(context);
        animationFadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        // iV = (Activity)context.findViewById(R.id.img_view);
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
        final View main_view = layoutInflater.from(context).inflate(R.layout.card_layout_selection_activity, container, false);
        final TextView tv = main_view.findViewById(R.id.text_view_1);
        tv.setText(listOfString.get(position));


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "card text: " + tv.getText(), Toast.LENGTH_SHORT).show();

                switch (tv.getText().toString().trim().toLowerCase()) {
                    case "life hacks":
                        i = new Intent(v.getContext(), LifeHacksActivity.class);

                        //ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this,,"logoTransition");
                        //Intent in = new Intent(v.getContext(),LifeHacksActivity.class);
                        //context.startActivity(in,activityOptionsCompat.toBundle());
                        break;
                    case "fun facts":
                        i = new Intent(v.getContext(), FunFactsActivity.class);
                        break;
                    case "user's choice":
                        i = new Intent(v.getContext(), ByUsers.class);
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
}
