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
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.example.jim.superferry.activity.ActionMain;

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

public class ProcessAccountCreate extends AsyncTask<String, String, String> {
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    URL url;
    private EditText edit_text_username;
    private EditText edit_text_password;
    String iD;
    private Activity activity;
    HttpURLConnection conn;
    CountDownTimer sessionTimer;
    Dialog pLoading;

    public ProcessAccountCreate(Activity activity, CountDownTimer sessionTimer, EditText edit_text_username, EditText edit_text_password, String iD){
        this.activity = activity;
        this.sessionTimer = sessionTimer;
        this.edit_text_username = edit_text_username;
        this.edit_text_password = edit_text_password;
        this.iD = iD;

        pLoading = new Dialog(activity, R.style.Theme_AppCompat);
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_progress_login, null);
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
            url = new java.net.URL(params[3]);
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
                    .appendQueryParameter("username",params[0])
                    .appendQueryParameter("password",params[1])
                    .appendQueryParameter("id",params[2]);
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
        pLoading.dismiss();
        if(result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if(imm.isActive()){
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
            Snackbar.make(rootView.findViewById(R.id.content_main),"No Internet Connection", Snackbar.LENGTH_LONG).show();
        }else {


            try{
                String sessionUser;
                String status;
                String msg;

                JSONObject json_data = new JSONObject(result);
                status = json_data.getString("status");
                sessionUser = json_data.getString("sessionUser");
                msg = json_data.getString("msg");

                if(status.equals("200")){
                   // Constants.SESSION_USER = sessionUser;

                    SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //editor.putString("sessionUser", Constants.SESSION_USER);
                    editor.putString(Constants.SESSION_USER, sessionUser);
                    editor.apply();

                    sessionTimer.cancel();
                    Intent intent = new Intent(activity, ActionMain.class);
                    activity.startActivity(intent);
                    activity.finish();
                }
                if(status.equals("500")){
                    Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }
}
