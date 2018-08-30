package com.example.jim.superferry.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.jim.superferry.R;
import com.example.jim.superferry.others.SeatAvail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim on 11/05/2018.
 */

public class SeatAvailListAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public SeatAvailListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(@Nullable SeatAvail object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SeatAvailHolder seatAvailHolder;
        View row;
        row = convertView;
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.adapter_seat_layout,parent,false);
            seatAvailHolder = new SeatAvailHolder();
            seatAvailHolder.tx_vessel2 = (TextView)row.findViewById(R.id.vesselseat);
            seatAvailHolder.tx_seatAvail = (TextView) row.findViewById(R.id.seat_avail);
            row.setTag(seatAvailHolder);

        }else {
            seatAvailHolder = (SeatAvailHolder)row.getTag();
        }

        SeatAvail seatAvail = (SeatAvail) this.getItem(position);
        seatAvailHolder.tx_vessel2.setText(seatAvail.getVessel());
        seatAvailHolder.tx_seatAvail.setText(seatAvail.getSeat_avail());

        return row;
    }

    static class SeatAvailHolder{
        TextView tx_vessel2, tx_seatAvail;
    }
}
