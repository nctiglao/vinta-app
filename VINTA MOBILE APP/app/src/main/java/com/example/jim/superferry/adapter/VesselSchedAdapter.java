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
import com.example.jim.superferry.others.VesselSced;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jim on 08/05/2018.
 */

public class VesselSchedAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public VesselSchedAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }


    public void add(@Nullable VesselSced object) {
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

        VesselSchedHolder vesselSchedHolder;
        View row;
        row = convertView;
        if(row==null){

            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.adapter_view_layout,parent, false);
            vesselSchedHolder = new VesselSchedHolder();
            vesselSchedHolder.tx_route = (TextView)row.findViewById(R.id.routeTxt);
            vesselSchedHolder.tx_vessel = (TextView) row.findViewById(R.id.vessellst);
            vesselSchedHolder.tx_departure = (TextView) row.findViewById(R.id.departure);
            row.setTag(vesselSchedHolder);
        }else {
            vesselSchedHolder = (VesselSchedHolder)row.getTag();
        }

        VesselSced vesselSced = (VesselSced) this.getItem(position);
        vesselSchedHolder.tx_route.setText(vesselSced.getRoute());
        vesselSchedHolder.tx_vessel.setText(vesselSced.getVessel_no());
        vesselSchedHolder.tx_departure.setText(vesselSced.getTime());

        return row;
    }

    static class VesselSchedHolder{

        TextView tx_route, tx_vessel, tx_departure;
    }
}
