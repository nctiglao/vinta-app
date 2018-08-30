package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.example.jim.superferry.activity.AccountDetails;
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
 * Created by jim on 16/03/2018.
 */

public class ProcessLogin extends AsyncTask<String, String, String>{
    HttpUrl httpUrl = new HttpUrl();
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    private Activity activity;
    private EditText editTextUser,editTextPass;
    private EditText editTextUsername,editTextPassword;
    String android_id;
    public final String LOGIN = "ProcessLogin";
    AlertDialog builder;
    ProgressBar dialog;
    Dialog pLoading;
    HttpURLConnection conn;
    java.net.URL url = null;
    SharedPreferences sp;


    public ProcessLogin(Activity activity, EditText editTextUsername, EditText editTextPassword, String android_id){
        this.activity = activity;
        this.editTextUsername = editTextUsername;
        this.editTextPassword = editTextPassword;
        this.android_id = android_id;

        pLoading = new Dialog(activity,R.style.Theme_AppCompat);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_progress_login, null);
        pLoading.setCancelable(false);
        pLoading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        pLoading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pLoading.setContentView(view);

    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        pLoading.show();

    }


    @Override
    protected String doInBackground(String... params) {
        try {
            url =  new java.net.URL(params[3]);
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
                    .appendQueryParameter("email", params[0])
                    .appendQueryParameter("pass_id", params[1])
                    .appendQueryParameter("app_id", params[2]);
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
        //builder.dismiss();
        if(result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
            Snackbar.make(rootView.findViewById(R.id.linearCon),"No Internet Connection", Snackbar.LENGTH_LONG).show();

        }else{
            if(result.isEmpty()) {
                Toast.makeText(activity, "adazcxzcx", Toast.LENGTH_LONG).show();
            }else {

                try{
                    String status;
                    String sessionUser;

                    JSONObject json_data = new JSONObject(result);
                    //status = json_data.getString("status");

                    //Log.d(LOGIN,status);


              if (json_data.getString("status").equals("200")){
                  sessionUser = json_data.getString("sessionUser");
                        //Constants.SESSION_USER = sessionUser;
                        SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.SESSION_USER, sessionUser);
                        // editor.putString("sessionUser", sessionUser);
                        editor.apply();

                        Intent intent = new Intent(activity, AccountDetails.class);
                        activity.startActivity(intent);
                        activity.finish();

                    }
                    if (json_data.getString("status").equals("500")){
                        Toast.makeText(activity, json_data.getString("msg"), Toast.LENGTH_SHORT).show();
              }
                  /*  if (status.equals("500")){
                        Toast.makeText(activity, json_data.getString("msg"), Toast.LENGTH_SHORT).show();
                    }*/
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
