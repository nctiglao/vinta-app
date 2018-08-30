package com.example.jim.superferry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.RideHistoryAdapter;

/**
 * Created by jim on 25/07/2018.
 */

public class RideHistory extends Activity {

    RideHistoryAdapter rideHistoryAdapter;
    ListView listView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ridehistory_listview);
        listView = (ListView) findViewById(R.id.ridelistView);

        rideHistoryAdapter = new RideHistoryAdapter(RideHistory.this, R.layout.adapter_ridehistory_layout);
        listView.setAdapter(rideHistoryAdapter);

        sharedPreferences = RideHistory.this.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String username = sharedPreferences.getString(Constants.USER_NAME, null);




    }
}
