package com.example.jim.superferry.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessGetName;
import com.example.jim.superferry.R;
import com.example.jim.superferry.others.BottomNavigationViewHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by jim on 21/03/2018.
 */

public class TicketScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView zXingScannerView;
    Button btnProceed;
    private DrawerLayout mDrawLayout;
    private ActionBarDrawerToggle mToggle;
    private TextView btnText;
    Vibrator vibrator;
    MediaPlayer mp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_scan_activity);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.click);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        checkCameraPermission();
        googleServicesAvailable();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent0 = new Intent(TicketScanActivity.this, ActionMain.class);
                     //   intent0.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        TicketScanActivity.this.startActivity(intent0);
                        finish();

                        break;

                    case R.id.nav_vesselETA:

                        break;

                    case R.id.nav_bottomAccount:
                        Intent intent = new Intent(TicketScanActivity.this, LoginActivity.class);
                    //    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        TicketScanActivity.this.startActivity(intent);
                        finish();

                        break;


                    case R.id.nav_about:
                        Intent intent1 = new Intent(TicketScanActivity.this, AboutActivity.class);
                     //   intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        TicketScanActivity.this.startActivity(intent1);
                        finish();
                        break;

                }

                return false;
            }
        });

       SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        //Constants.SESSION_USER = sp.getString("sessionUser", "");
        sp.getString(Constants.SESSION_USER,null);


        //account_details();
    }


   /* public void selectedDrawer(MenuItem menuItem) {
        Fragment myFragment = null;
        Class fragmentClass = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_account:
                fragmentClass = MyAccountFragment.class;
                break;
            case R.id.nav_seatAvail:
                fragmentClass = SeatAvailable.class;
                break;
            case R.id.nav_main:
                fragmentClass = BlankFragment.class;
                break;


        }
        try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relativeContent,myFragment).commit();
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawLayout.closeDrawers();
    }
*/


   /* private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectedDrawer(item);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar, menu);
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){
            case R.id.action_logout:
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(this, LoginActivity.class);
                this.startActivity(intent);
                this.finish();
                break;
        }
        return true;
    }
*/
   public void scan (View view) {

       vibrator.vibrate(50);
       mp.start();

       SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
/*       sp.getString("sessionUser", "");
       if(sp.contains("sessionUser")) {
           zXingScannerView = new ZXingScannerView(getApplicationContext());
           setContentView(zXingScannerView);
           zXingScannerView.setResultHandler(this);
           zXingScannerView.startCamera();*/

       sp.getString(Constants.SESSION_USER, null);

       Constants.SESSION_USERS = sp.getString(Constants.SESSION_USER, null);
       if(sp.contains(Constants.SESSION_USER)) {
           zXingScannerView = new ZXingScannerView(getApplicationContext());
           setContentView(zXingScannerView);
           zXingScannerView.setResultHandler(this);
           zXingScannerView.startCamera();
       }else{
           AlertDialog.Builder builder = new AlertDialog.Builder(this);
           builder.setTitle("Error.");
           builder.setMessage("Please login first. \n Thank you!");
           builder.setCancelable(false);
           builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                   dialogInterface.cancel();
               }
           });

           AlertDialog alertDialog = builder.create();
           alertDialog.show();
       }


    }





   @Override
    public void handleResult(Result rawResult) {

       Constants.SCAN_RESULT = rawResult.getText();

       SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES3, Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
       editor.putString("scanKey", rawResult.getText());
       editor.apply();

       zXingScannerView.stopCamera();
        Toast.makeText(this, Constants.SCAN_RESULT, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ActivityMain.class);
            this.startActivity(intent);
            this.finish();



    }

    @Override
    public void onBackPressed() {
       /* new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TicketScanActivity.super.onBackPressed();
                    }
                }).create().show();*/
       super.onBackPressed();
    }



    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.CAMERA},
                        Constants.REQUEST_PERMISSION_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.CAMERA},
                        Constants.REQUEST_PERMISSION_CAMERA);
            }
            return false;

        } else
            return false;
    }
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to Play Services", Toast.LENGTH_LONG).show();
            finish();
        }
        return false;
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Camera");
                    builder.setMessage("You need to enable Camera permission to continue.");
                    builder.setCancelable(true);

                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = null;
                            if (android.os.Build.VERSION.SDK_INT >= 26) {
                                intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);

                                Uri uri = Uri.fromParts("package", TicketScanActivity.this.getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, Constants.REQUEST_PERMISSION_CAMERA);
                            }
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;


        }
    }


    private void account_details(){
            String pref_data = Constants.SESSION_USER;
        new ProcessGetName(TicketScanActivity.this, Constants.SESSION_USER).execute(pref_data, Constants.GET_PROFILE);
    }
}
