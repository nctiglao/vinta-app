package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.example.jim.superferry.activity.AccountActivateCreate;

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

public class ProcessAccountValidate extends AsyncTask<String, String, String> {
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    private Activity activity;
    private EditText editTextContact,editActivationCode, editBirthday;
    private EditText editTextUser;
    Dialog pLoading;
    HttpURLConnection conn;


    java.net.URL url = null;

    public ProcessAccountValidate(Activity activity, EditText editTextContact, EditText editTextUser, EditText editBirthday, EditText editActivationCode)
    {
        this.activity = activity;

        this.editTextContact = editTextContact;
        this.editTextUser = editTextUser;
        this.editBirthday = editBirthday;
        this.editActivationCode = editActivationCode;

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
            url = new java.net.URL(params[4]);
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
                    .appendQueryParameter("fname",params[0])
                    .appendQueryParameter("lname",params[1])
                    .appendQueryParameter("dob",params[2])
                    .appendQueryParameter("code",params[3]);
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
            Snackbar.make(rootView.findViewById(R.id.content_main_create_customer_account),"No Internet Connection", Snackbar.LENGTH_LONG).show();
        }else {

            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
            try{

                String status;
                String code;
                String sessionKey;
                int cd = 0;
                String msg;




                    JSONObject json_data = new JSONObject(result);
                    status = json_data.getString("status");
                    sessionKey = json_data.getString("sessionKey");
                    code = json_data.getString("code");
                    cd = json_data.getInt("cd");
                    msg = json_data.getString("msg");




                //Toast.makeText(activity, Constants.CODE,Toast.LENGTH_LONG).show();

                if(status.equals("200")){
                    Constants.SESSION_KEY = sessionKey;
                    Constants.SESSION_TIMER = cd;
                    Constants.CODE = code;
                    Toast.makeText(activity, Constants.SESSION_KEY, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, AccountActivateCreate.class);
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
