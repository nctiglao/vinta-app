package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
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
 * Created by jim on 21/08/2018.
 */

public class ProcessResendVerification extends AsyncTask<String, String, String> {

    HttpUrl httpUrl = new HttpUrl();
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    private Activity activity;
    Dialog pLoading;
    public String email;
    public String pass_id;
    public String mobile;
    String android_id;
    HttpURLConnection conn;
    java.net.URL url = null;

    public ProcessResendVerification(Activity activity, String email, String pass_id, String mobile, String android_id){
        this.activity = activity;
        this.email = email;
        this.pass_id = pass_id;
        this.mobile = mobile;
        this.android_id = android_id;
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
            url = new java.net.URL(Constants.VERIFY_ACCOUNT);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
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
                    .appendQueryParameter("mobile", params[2])
                    .appendQueryParameter("app_id", params[3]);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
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
                return ("unsuccessful");
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

        if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
            View rootView = activity.getWindow().getDecorView().findViewById(R.id.content_verify_account);
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
            Snackbar.make(rootView.findViewById(R.id.container_seats), "No Internet Connection", Snackbar.LENGTH_LONG).show();
        } else {
            if (result.equals("no result")) {
                Toast.makeText(activity, "" + result, Toast.LENGTH_LONG).show();
            } else {
                try {
                    String status, schedule;
                    JSONObject json_data = new JSONObject(result);
                    status = json_data.getString("status");

                    if (status.equals("200")) {
                        Toast.makeText(activity, " ", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(activity, "Invalid Information.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
