package com.example.jim.superferry.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.SlideAdapter;

/**
 * Created by jim on 16/08/2018.
 */

public class SlidingApp extends AppCompatActivity {
    ViewPager viewPager;
    LinearLayout linearLayout;

    private SlideAdapter slideAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_appslide);

        viewPager = (ViewPager) findViewById(R.id.slideViewPager);
        linearLayout = (LinearLayout) findViewById(R.id.dotLinear);

        slideAdapter = new SlideAdapter(this);
        viewPager.setAdapter(slideAdapter);


    }
}
