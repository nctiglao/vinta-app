/*

package com.example.jim.superferry.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jim.superferry.R;
import com.example.jim.superferry.others.CardView;

import java.util.ArrayList;


*/
/**
 * Created by jim on 27/04/2018.
 *//*



public class VesselScheduleAdapter extends RecyclerView.Adapter<VesselScheduleAdapter.VesselScheduleHolder>{

    private ArrayList<CardView> mData;
    private Activity activity;

    public VesselScheduleAdapter(ArrayList<CardView> data, Activity activity) {
        this.mData = data;
        this.activity = activity;
    }

    public VesselScheduleAdapter.VesselScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cardviewer, parent, false);
        return new VesselScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(VesselScheduleAdapter.VesselScheduleHolder holder, int position) {

        CardView cardView = mData.get(position);

        holder.setVessel_no(cardView.getVessel_no());
        holder.setTime(cardView.getTime());

    }

    @Override
    public int getItemCount() {
        if (mData == null)
        return 0;

        return mData.size();
    }



    public class VesselScheduleHolder extends RecyclerView.ViewHolder {

        TextView vesseltext_no;
        TextView station_origtext;
        TextView station_desttext;
        TextView timetext;

        public VesselScheduleHolder(View itemView) {
            super(itemView);


            vesseltext_no = (TextView) itemView.findViewById(R.id.vesselCardViewVal);
            station_origtext = (TextView) itemView.findViewById(R.id.stationOrigCardViewVal);
            station_desttext= (TextView) itemView.findViewById(R.id.stationDestCardViewVal);
            timetext = (TextView) itemView.findViewById(R.id.timeSchedCardViewVal);

        }


        public void setVessel_no(String vessel_no) {
            vesseltext_no.setText(vessel_no);
        }

        public void setStation_orig(String station_orig) {station_origtext.setText(station_orig);
        }

        public void setStation_dest(String station_dest) {
            station_desttext.setText(station_dest);
        }

        public void setTime(String time) {
            timetext.setText(time);
        }


    }


}

*/
