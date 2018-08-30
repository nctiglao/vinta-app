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
import com.example.jim.superferry.others.RideHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim on 25/07/2018.
 */

public class RideHistoryAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public RideHistoryAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);

    }

    public void add(@Nullable RideHistory object) {
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

        RideHistoryAdapter.RideHistoryHolder rideHistoryHolder;
        View row;
        row = convertView;
        if(row==null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.adapter_ridehistory_layout,parent,false);
            rideHistoryHolder = new RideHistoryAdapter.RideHistoryHolder();
            rideHistoryHolder.tx_id = (TextView)row.findViewById(R.id.vesselCardViewVal);
            rideHistoryHolder.tx_origin = (TextView) row.findViewById(R.id.stationOrigCardViewVal);
            rideHistoryHolder.tx_destination = (TextView) row.findViewById(R.id.stationDestCardViewVal);
            rideHistoryHolder.tx_date = (TextView) row.findViewById(R.id.timeSchedCardViewVal);
            row.setTag(rideHistoryHolder);

        }else {
            rideHistoryHolder = (RideHistoryAdapter.RideHistoryHolder)row.getTag();
        }

        RideHistory rideHistory = (RideHistory) this.getItem(position);
        rideHistoryHolder.tx_id.setText(rideHistory.getId());
        rideHistoryHolder.tx_origin.setText(rideHistory.getDestination());
        rideHistoryHolder.tx_destination.setText(rideHistory.getDestination());
        rideHistoryHolder.tx_date.setText(rideHistory.getDate());

        return row;
    }

    static class RideHistoryHolder{
        TextView tx_id, tx_origin, tx_destination, tx_date;
    }
}


