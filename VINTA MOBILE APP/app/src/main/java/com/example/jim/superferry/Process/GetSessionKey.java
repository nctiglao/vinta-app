package com.example.jim.superferry.Process;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.widget.Toast;

import com.example.jim.superferry.activity.AccountActivateCreate;
import com.example.jim.superferry.dialog.ErrorDialogMsg;
import com.example.jim.superferry.others.Holder;
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
import java.net.URL;

/**
 * Created by jim on 19/03/2018.
 */

public class GetSessionKey extends AsyncTask<String, String, String> {

    CountDownTimer cd;
    HttpUrl httpUrl = new HttpUrl();
    URL url;
    HttpURLConnection conn;
    Holder holder = new Holder();
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public String code;
    Activity activity;
    public GetSessionKey(Activity activity, String code ){


        this.activity = activity;
        this.code = code;

    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }


    @Override
    protected String doInBackground(String... params) {

        try {
            url = new java.net.URL(params[1]);
        }catch (MalformedURLException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "exception";
        }
        try {
            conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");

            conn.setDoInput(true);
            conn.setDoOutput(true);
            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("code",params[0]);
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
            return e1.toString();
        }

        try {

            int response_code = conn.getResponseCode();

            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return (result.toString());

            } else {
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
       if(result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            new ErrorDialogMsg(activity,"Opps.. Something wrong. \n No internet connection");
            Toast.makeText(activity, "no rows", Toast.LENGTH_LONG).show();
        }else{
            if(result.equals("no result")){
                Toast.makeText(activity,""+result,Toast.LENGTH_LONG).show();
            }else {
                try {
                    String status;

                    JSONObject jsonObject = new JSONObject(result);
                        status = jsonObject.getString("status");

                    if (status.equals("activated")){
                        Toast.makeText(activity, status, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(activity.getApplicationContext(), AccountActivateCreate.class);
                        activity.startActivity(intent);
                        activity.finish();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }
    }
}
