package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.example.jim.superferry.activity.TicketScanActivity;

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

public class ProcessCheckPermission extends AsyncTask<String, String, String> {

    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    View rootView;
    Activity activity;
    ProgressDialog dialog;
    HttpURLConnection conn;
    java.net.URL url = null;


    String name;



    public ProcessCheckPermission(Activity activity, String name) {

        this.activity = activity;
        this.name = name;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(activity, "Processing......", Toast.LENGTH_LONG).show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            url = new java.net.URL(params[1]);
        }catch (MalformedURLException e){
            e.printStackTrace();
            return "exception";
        }
        try{
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("sessionUser",params[0]);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os));
            writer.write(query);
            writer.flush();
            writer.close();
            os.flush();
            conn.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
            return "exception";
        }
        try {
            int reponse_code = conn.getResponseCode();

            if(reponse_code == HttpURLConnection.HTTP_OK){

                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result =  new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null){
                    result.append(line);
                }
                return (result.toString());
            } else {
                return ("unsuccessful");
            }
        }catch (IOException e){
            e.printStackTrace();
            return e.toString();
        }finally {
            conn.disconnect();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Toast.makeText(activity, "Processed......", Toast.LENGTH_LONG).show();
        if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
        }else {
            String status = null;
           // String sessionUser;
            try {
                JSONObject json = new JSONObject(result);
                status = json.getString("status");
             //   sessionUser = json.getString("session_key");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(status.equals("success")){
                Intent intent = new Intent(activity, TicketScanActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }else if(status.equals("sessionInvalid")){
                Toast.makeText(activity,",..",Toast.LENGTH_LONG).show();
            }else{
            }
        }

    }

}
