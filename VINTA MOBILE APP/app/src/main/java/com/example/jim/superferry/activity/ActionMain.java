package com.example.jim.superferry.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jim.superferry.Fragment.SeatAvailable;
import com.example.jim.superferry.Fragment.VesselSchedFragment;
import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.SectionPageAdapter;
import com.example.jim.superferry.others.BottomNavigationViewHelper;

//import com.example.jim.superferry.adapter.VesselScheduleAdapter;
//import com.example.jim.superferry.others.CardView;

/**
 * Created by jim on 16/04/2018.
 */

public class ActionMain extends AppCompatActivity {

    private static final String TAG = "ActionMain";

    private SectionPageAdapter mSectionPageAdapter;

    private RecyclerView schedRecycleViewer;
   // private VesselScheduleAdapter mAdapter;
   // private ArrayList<CardView> cardViewsCollection;
    private ViewPager mViewPager;



    SharedPreferences sp;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_action);



        mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_event_seat_black_24dp).setText("Seat Availability");
       // tabLayout.getTabAt(0).setCustomView(tv);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_schedule_black_24dp).setText("Trip Schedule");

       // init();


       /* mSectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout  = (TabLayout) findViewById(R.id.tab);
        tabLayout.setupWithViewPager(mViewPager);*/

       /* if (!isConnected(ActionMain.this)) buildDialog(ActionMain.this).show();
        else {

        }*/

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);







        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:

                        break;

                    case R.id.nav_vesselETA:
                        Intent intent1 = new Intent(ActionMain.this, TicketScanActivity.class);
                        //intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        ActionMain.this.startActivity(intent1);
                        finish();

                        break;

                    case R.id.nav_bottomAccount:
                        Intent intent = new Intent(ActionMain.this, LoginActivity.class);
                     //   intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        ActionMain.this.startActivity(intent);
                        finish();

                        break;

                    case R.id.nav_about:
                        Intent intent3 = new Intent(ActionMain.this, AboutActivity.class);
                       // intent3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        ActionMain.this.startActivity(intent3);
                        finish();
                        break;

                }

                return false;
            }
        });



        //Toast.makeText(ActionMain.this, sp.getString("sessionUser", ""), Toast.LENGTH_LONG).show();

    }




  /*  private void init() {
        schedRecycleViewer = (RecyclerView)findViewById(R.id.schedRecycle);
        schedRecycleViewer.setLayoutManager(new LinearLayoutManager(this));
        schedRecycleViewer.setHasFixedSize(true);
        cardViewsCollection = new ArrayList<>();
        schedRecycleViewer.setAdapter(mAdapter);
    }*/


    public boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return false;

            else return false;
        } else

            return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;

    }




    private void setupViewPager(ViewPager mViewPager) {

      SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new SeatAvailable());
        adapter.addFragment(new VesselSchedFragment());

        mViewPager.setAdapter(adapter);

    }




}
