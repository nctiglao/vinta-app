package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.R;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jim on 06/04/2018.
 */

public class ProcessAccountDetails extends AsyncTask<String, String, String> {
    HttpUrl httpUrl = new HttpUrl();
    URL url;
    HttpURLConnection conn;
    Holder holder = new Holder();
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    public String pref_data;
    Activity activity;
    Dialog pLoading;

    public ProcessAccountDetails(Activity activity, String pref_data){
        this.activity = activity;
        this.pref_data = pref_data;

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
        pLoading.dismiss();
        if(result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")){
            new ErrorDialogMsg(activity,"Opps.. Something wrong. \n No internet connection");
            Toast.makeText(activity, "no rows", Toast.LENGTH_LONG).show();
        }else{
            if(result.equals("no result")){
                Toast.makeText(activity,""+result,Toast.LENGTH_LONG).show();
            }else {
                try {
                    String status;
                    String fname;
                    String lname;
                    String picture;
                    String msg;


                    JSONObject jsonObject = new JSONObject(result);
                  //  Toast.makeText(activity, jsonObject.getString("status"), Toast.LENGTH_SHORT).show();

                  //  Toast.makeText(activity, jsonObject.getString("fname"), Toast.LENGTH_SHORT).show();
                    status = jsonObject.getString("status");
                    fname = jsonObject.getString("fname");
                    lname = jsonObject.getString("lname");
                    picture = jsonObject.getString("picture");





                 if (jsonObject.getString("status").equals("200")){

                       byte[] decodeString = Base64.decode( jsonObject.getString("picture"), Base64.DEFAULT);
                       Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);


                        ((TextView) activity.findViewById(R.id.AccFnametxtView)).setText(jsonObject.getString("fname"));
                        ((TextView) activity.findViewById(R.id.AccLnametxtView)).setText(jsonObject.getString("lname"));
                        ((CircleImageView) activity.findViewById(R.id.user_profile)).setImageBitmap(decoded);

                  /*      SharedPreferences sharedPreferences = activity.getSharedPreferences(Constants.SHARED_PREFERENCES2, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userPic", picture);
                        editor.putString("userFname", fname);
                        editor.putString("userLname", lname);
                        editor.apply();*/


                    }

                    if(jsonObject.getString("status").equals("500")){
                        msg = jsonObject.getString("msg");
                        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }


            }
        }
    }

}
