package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.example.jim.superferry.activity.ActionMain;
import com.example.jim.superferry.dialog.ErrorDialogMsg;
import com.example.jim.superferry.others.HttpUrl;

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
 * Created by jim on 10/05/2018.
 */

public class ProcessLogout extends AsyncTask<String,String,String> {
    HttpUrl httpUrl = new HttpUrl();
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    private Activity activity;
    Dialog pLoading;
    public String pref_data;
    HttpURLConnection conn;
    java.net.URL url = null;

    public ProcessLogout(Activity activity, String pref_data){
        this.activity = activity;
        this.pref_data = pref_data;


        pLoading = new Dialog(activity, R.style.Theme_AppCompat);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_progress_logout, null);
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
            url =  new java.net.URL(params[1]);
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
                    .appendQueryParameter("sessionUser",params[0]);
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
            new ErrorDialogMsg(activity,"Opps.. Something wrong. \n No internet connection");
            Snackbar.make(activity.findViewById(R.id.linearCon),"No Internet Connection", Snackbar.LENGTH_LONG).show();
            Toast.makeText(activity, "no rows", Toast.LENGTH_LONG).show();
        }else{
            if(result.equals("no result")) {
                Toast.makeText(activity, "" + result, Toast.LENGTH_LONG).show();
            }else {
                try{
                    String status;

                    JSONObject json_data = new JSONObject(result);
                    status = json_data.getString("status");
                    if (status.equals("200")){
                        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.apply();

                        clearProfile();

                        Toast.makeText(activity, "Logout successfully",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(activity, ActionMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        activity.startActivity(intent);
                        activity.finish();
                        System.exit(0);


                    }else{

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public void clearProfile(){

        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFERENCES2, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
