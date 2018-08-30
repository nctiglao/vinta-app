package com.example.jim.superferry.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jim.superferry.R;

/**
 * Created by jim on 16/08/2018.
 */

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }

    //array

    public int[] slideimage ={
        R.drawable.ic_event_seat_black_24dp,
        R.drawable.ic_schedule_black_24dp,
        R.drawable.ic_map_black_24dp
    };

    public String[] slideHeading = {
            "SEAT AVAILABILITY",
            "TRIP SCHEDULE",
            "MAP TRACKING"
    };

    public String[] slidedesc = {
            "This is a sample body",
            "This is a sample body",
            "This is a sample body"
    };

    @Override
    public int getCount() {
        return slideHeading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageSlide);
        TextView headtextView = (TextView) view.findViewById(R.id.headingSlide);
        TextView bodyTextView = (TextView) view.findViewById(R.id.bodySlide);

        imageView.setImageResource(slideimage[position]);
        headtextView.setText(slideHeading[position]);
        bodyTextView.setText(slidedesc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
     container.removeView((RelativeLayout) object);
    }
}
