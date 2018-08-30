package com.example.jim.superferry.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;

import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

/**
 * Created by jim on 11/05/2018.
 */

public class SplashScreen extends AppCompatActivity  {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Branch.getAutoInstance(this);
        SharedPreferences sp = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        sp.getString(Constants.SESSION_USER, "");
        if(sp.contains(Constants.SESSION_USER)) {
            Intent intent = new Intent(getApplicationContext(), ActionMain.class);
            startActivity(intent);
            finish();
        }else {

            splashScreen();

        }
    }

    private void splashScreen(){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);

                    Intent intent = new Intent(getApplicationContext(), ActionMain.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();

        // Branch init
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
                    Log.i("BRANCH SDK", referringParams.toString());
                } else {
                    Log.i("BRANCH SDK", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
