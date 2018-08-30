package com.example.jim.superferry.Process;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.VesselSchedAdapter;
import com.example.jim.superferry.others.VesselSced;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Iterator;

/**
 * Created by jim on 08/05/2018.
 */

public class ProcessGetSchedule extends AsyncTask<String, String, String> {
    public final int CONNECTION_TIMEOUT = 10000;
    public final int READ_TIMEOUT = 15000;
    private Activity activity;
    HttpURLConnection conn;
    java.net.URL url = null;
    VesselSchedAdapter vesselSchedAdapter;
    Dialog pLoading;


    ListView listView;

    public ProcessGetSchedule(Activity activity, VesselSchedAdapter vesselSchedAdapter, ListView listView){
        this.activity = activity;
        this.vesselSchedAdapter = vesselSchedAdapter;
        this.listView = listView;

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
            url = new java.net.URL(Constants.GET_SCHEDULE);
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
        /*    Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username",params[0]);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(os));
            writer.write(query);
            writer.flush();
            writer.close();
            os.flush();*/
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
    protected void  onPostExecute(String result) {
        super.onPostExecute(result);
            pLoading.dismiss();

            if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {
                View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                if(imm.isActive()){
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                }
                Snackbar.make(rootView.findViewById(R.id.container),"No Internet Connection", Snackbar.LENGTH_LONG).show();

            }else {
                if (result.equals("no result")) {
                    Toast.makeText(activity, "" + result, Toast.LENGTH_SHORT).show();
                } else {


                    try {

                        String data;

                        JSONObject json_data = new JSONObject(result);
/*
                        data = json_data.getString("data");
*/
                        JSONArray jArray = json_data.getJSONArray("data");

                        int i = 0;
                        String route, vessel, departure;
                       while (i<jArray.length()) {
                            JSONObject JO = jArray.getJSONObject(i);

                            route = JO.getString("route");
                            vessel = JO.getString("vessel");
                            departure = JO.getString("departure");

                            VesselSced vesselSced = new VesselSced(route, vessel, departure);

                            vesselSchedAdapter.add(vesselSced);

                            i++;
                        }







                        Toast.makeText(activity, "" + json_data.get("vessel"), Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    private <T> Iterable<T> iterate(final Iterator<T> i){
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return i;
            }
        };
    }
}
