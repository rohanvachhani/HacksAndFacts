package com.rohan.hacksandfacts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class AdapterForSelectionList extends PagerAdapter {
    private final List<String> listOfString;
    private final Context context;
    private final LayoutInflater layoutInflater;


    static Intent i = null;

    public AdapterForSelectionList(Context applicationContext, List<String> listOfString) {
        this.listOfString = listOfString;
        this.context = applicationContext;
        this.layoutInflater = LayoutInflater.from(context);
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
