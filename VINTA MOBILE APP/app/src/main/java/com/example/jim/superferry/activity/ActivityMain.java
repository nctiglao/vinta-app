package com.example.jim.superferry.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class ActivityMain extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener{

    private TextView MyLocValLat;
    private TextView MyLocValLong;
    private TextView MyDestValLat;
    private TextView MyDestValLong;
    private TextView VesselNoVal;
    private TextView StationOrigVal;
    private TextView ETAVal;
    private TextView TravelDistanceVal;
    private TextView StationDestVal;
    private TextView TimeDurationVal;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private String mUsername;
    private static final String TAG = ActivityMain.class.getSimpleName();
    public static final int REQUEST_PERMISSION_LOCATION = 1;
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;
    Marker marker ;
    int x = 0;
    private Boolean isConnected = true;

    SharedPreferences sp1;


    private  IO.Options opts;
    {

        opts = new IO.Options();
        opts.query = "token=" + Constants.SESSION_USERS + "&payment_id="+Constants.SCAN_RESULT +"&type=payment";
        opts.reconnection = true;

    }

    private Socket mSocket;
    {
        try{
            mSocket = IO.socket(Constants.SERVER_URL1, opts);
        }catch (URISyntaxException e) {

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

        mSocket.connect();
        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on("tracking", onNewMessage2);

        Toast.makeText(this, Constants.SESSION_USERS, Toast.LENGTH_LONG).show();

            magFragment();

        checkLocationPermission();
        googleServicesAvailable();


        ((TextView)findViewById(R.id.paymentID)).setText(Constants.SCAN_RESULT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_toolbar, menu);
        return true;
    }

    @Override
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

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(!isConnected) {
                        Toast.makeText(ActivityMain.this, R.string.connect, Toast.LENGTH_LONG).show();
                        isConnected = true;
                    }
                }
            });
        }
    };


    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "diconnected");
                    isConnected = false;
                    Toast.makeText(ActivityMain.this,
                            R.string.disconnect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "Error connecting");
                    Toast.makeText(ActivityMain.this,
                            R.string.error_connect, Toast.LENGTH_LONG).show();
                }
            });
        }
    };



    private Emitter.Listener onNewMessage2 = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    float lat;
                    float lng;
                    String vessel_id;
                    String station_origin;
                    String station_dest;
                    JSONObject eta;
                    String speed;
                    String distance;
                    String eta_time;
                    String eta_duration;
                    float heading;

                    try{
                        lat = Float.parseFloat(data.getString("lat"));
                        lng = Float.parseFloat(data.getString("lng"));
                        heading = Float.parseFloat(data.getString("bearing"));
                        speed = data.getString("speed");
                        vessel_id = data.getString("vessel");
                        station_origin = data.getString("origin");
                        station_dest = data.getString("dest");
                        distance = data.getString("distance");
                        eta = data.getJSONObject("eta");
                        eta_time = eta.getString("time");
                        eta_duration = eta.getString("duration");



                        ((TextView) findViewById(R.id.StationOrigVal)).setText(station_origin);
                        ((TextView) findViewById(R.id.StationDestVal)).setText(station_dest);
                        ((TextView) findViewById(R.id.ETAVal)).setText(eta_time);
                        ((TextView) findViewById(R.id.VesselNoVal)).setText(vessel_id);
                        ((TextView) findViewById(R.id.txtSpeedVal)).setText(speed);
                        ((TextView) findViewById(R.id.txtDistanceVal)).setText(distance);
                        ((TextView)findViewById(R.id.DurationVal)).setText(eta_duration);
                        ((TextView)findViewById(R.id.paymentID)).setText(""+heading);
                        LatLng vessel = new LatLng(lat,lng);









                        if(marker != null){
                            marker.setPosition(vessel);
                            marker.setTitle(vessel_id);
                           // marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                            marker.isFlat();
                          marker.setRotation(heading);
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vessel, 15f));
                            mGoogleMap.animateCamera(CameraUpdateFactory.zoomIn());
                            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15f), 2000, null);
                        }else{
                            marker = mGoogleMap.addMarker(new MarkerOptions()
                            .position(vessel)
                            .rotation(heading)
                            .flat(true)
                            .title(vessel_id));
                        }


           /*             NotificationCompat.Builder builder = new NotificationCompat.Builder(ActivityMain.this);
                        Intent intent = new Intent(ActivityMain.this, ActivityMain.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(ActivityMain.this,1,intent,0);

                        if (notif != null) {
                            x++;
                            if (x == 11) {
                                builder.setContentIntent(pendingIntent);
                                builder.setDefaults(Notification.DEFAULT_ALL);
                                builder.setContentTitle("Super Ferry");
                                builder.setSmallIcon(R.mipmap.ic_launcher);
                                builder.setContentText(notif);
                                builder.setPriority(Notification.PRIORITY_DEFAULT);
                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(001, builder.build());
                                x=0;
                            }
                        }
*/

                    }catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                  String message;

                    try{
                        message = data.getString("status");


                        if(message != null){

                            Toast.makeText(ActivityMain.this, message, Toast.LENGTH_LONG).show();
                        }


                        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vessel, 20f));

                    }catch (JSONException e) {
                        return;
                    }
                }
            });
        }
    };




    private void magFragment(){
      MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off(Socket.EVENT_CONNECT,onConnect);
        mSocket.off(Socket.EVENT_DISCONNECT,onDisconnect);
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.off("tracking", onNewMessage2);



    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission granted
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    {
                        if(mGoogleApiClient == null)
                        {
                            mGoogleApiClient = new GoogleApiClient.Builder(this)
                                    .addApi(LocationServices.API)
                                    .addConnectionCallbacks(this)
                                    .addOnConnectionFailedListener(this)
                                    .build();

                            mGoogleApiClient.connect();

                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                }
                else //permission denied
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityMain.this);

                    builder.setTitle("Location");
                    builder.setMessage("You need to enable Location permission to continue.");
                    builder.setCancelable(true);

                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", ActivityMain.this.getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, REQUEST_PERMISSION_LOCATION);

                        }

                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                break;
        }




        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();
  //          mGoogleMap.setMyLocationEnabled(true);
        }else{
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();

            mGoogleApiClient.connect();
            mGoogleMap.setMyLocationEnabled(true);
        }
        
    }


    //------------Function--------///
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_PERMISSION_LOCATION);
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

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null){
            Toast.makeText(this, "Can't get current location", Toast.LENGTH_LONG).show();
           /* ((TextView) findViewById(R.id.MyDestValLat)).setText(Constants.DEFAULT_DATA);
            ((TextView) findViewById(R.id.MyDestValLong)).setText(Constants.DEFAULT_DATA);*/
        }else{
            double  lat = location.getLatitude();
            double lng = location.getLongitude();

            /*((TextView) findViewById(R.id.MyLocValLat)).setText("" + lat);
            ((TextView) findViewById(R.id.MyLocValLong)).setText("" + lng);
            //Toast.makeText(this, "LAT AND LNG" +lat +"," +lng, Toast.LENGTH_LONG).show()*/;


        }
    }

    @Override
    public void onBackPressed() {
         AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setTitle("Exit");
                builder.setMessage("Are you sure you want to exit?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mSocket.disconnect();
                        mSocket.off(Socket.EVENT_CONNECT,onConnect);
                        mSocket.off(Socket.EVENT_DISCONNECT,onDisconnect);
                        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError);
                        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
                        mSocket.off("tracking", onNewMessage2);

                        Intent intent =  new Intent(ActivityMain.this, TicketScanActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        ActivityMain.this.startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                });
        builder.create().show();



    }
}
