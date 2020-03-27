package com.rohan.hacksandfacts;

import android.content.Context;
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
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View main_view = layoutInflater.from(context).inflate(R.layout.card_layout, container, false);
        TextView tv = main_view.findViewById(R.id.text_view_1);
        tv.setText(listOfString.get(position));

        main_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
