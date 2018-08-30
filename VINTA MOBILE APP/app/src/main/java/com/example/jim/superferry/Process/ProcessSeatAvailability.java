package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.SeatAvailListAdapter;
import com.example.jim.superferry.others.SeatAvail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by jim on 21/03/2018.
 */

public class ProcessSeatAvailability extends AsyncTask<String, String, String> {
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    private Activity activity;
    HttpURLConnection conn;
    java.net.URL url = null;
    String station_origin;
    String station_dest;
    String station_origin2;
    String station_dest2;
    Dialog pLoading;
    AlertDialog.Builder dialog;
    SeatAvailListAdapter seatAvailListAdapter;

    public ProcessSeatAvailability(Activity activity,String station_origin, String station_dest, String station_origin2, String station_dest2,SeatAvailListAdapter seatAvailListAdapter){
        this.activity = activity;
        this.station_origin = station_origin;
        this.station_dest = station_dest;
        this.station_origin2 = station_origin2;
        this.station_dest2 = station_dest2;
        this.seatAvailListAdapter = seatAvailListAdapter;


        pLoading = new Dialog(activity, R.style.Theme_AppCompat);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_progress_dialog, null);
        pLoading.setCancelable(false);
        pLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pLoading.setContentView(view);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pLoading.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            url =  new java.net.URL(params[4]);
        }catch (MalformedURLException e){
            e.printStackTrace();
            return "exception";
        }
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("origin", params[0])
                    .appendQueryParameter("dest", params[1]);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new
                    OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();
            conn.connect();
        }catch (IOException e1){
            e1.printStackTrace();
            return "exception";
        }
        try {
            int response_code = conn.getResponseCode();
            // Check if successful connection made
            if (response_code == HttpURLConnection.HTTP_OK) {
                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                // Pass data to onPostExecute method
                return(result.toString());
            }else{
                return("unsuccessful");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
            conn.disconnect();
        }
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pLoading.dismiss();
        if(result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
            Snackbar.make(rootView.findViewById(R.id.container_seats),"No Internet Connection", Snackbar.LENGTH_LONG).show();
        }else{
            if(result.equals("no result")) {
                Toast.makeText(activity, "" + result, Toast.LENGTH_LONG).show();
            }else {
                try{
                    String status, schedule;
                    String vessel, number;
                    String seat;
                    String seats;
                    String total_seats;
                    JSONObject json_data = new JSONObject(result);
                    status = json_data.getString("status");

                    seat = json_data.getString("seats");
                    if (status.equals("200")){


                      JSONObject JO =  json_data.getJSONObject("seats");
                        total_seats = JO.getString("total_seats");





                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                        alertDialog.setCancelable(true);
                        alertDialog.setTitle("Seat Available");
                        alertDialog.setMessage( station_origin2 +" - "+station_dest2 +"\n"+ "Total Available: "+ total_seats);

                        View convertView = (View) activity.getLayoutInflater().inflate(R.layout.layout_seat_listview, null);
                        final ListView listView = (ListView) convertView.findViewById(R.id.seatlistView);
                        seatAvailListAdapter = new SeatAvailListAdapter(activity, R.layout.adapter_seat_layout);
                        listView.setAdapter(seatAvailListAdapter);
                        alertDialog.setView(convertView);

                        alertDialog.setNegativeButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });


                        alertDialog.create().show();



                        JSONArray jsonArray = JO.getJSONArray("vessels");


                        for (  int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            vessel = jsonObject.getString("vessel");
                            number = jsonObject.getString("seats");


                                SeatAvail seatAvail = new SeatAvail(vessel, number);
                            seatAvailListAdapter.add(seatAvail);

                        }


                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                        builder.setTitle("Seat Available.");
                        builder.setMessage("No data found....");
                        builder.setCancelable(true);

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }
    }


}
