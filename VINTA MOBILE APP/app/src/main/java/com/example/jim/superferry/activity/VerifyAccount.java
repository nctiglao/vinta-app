package com.example.jim.superferry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessVerifyAccount;
import com.example.jim.superferry.R;

/**
 * Created by jim on 21/08/2018.
 */

public class VerifyAccount extends Activity {
    EditText emailEditText;
    EditText passEditText;
    EditText mobileEditText;
    Button verifyButton;
    Vibrator vibrator;
    MediaPlayer mp;
    TelephonyManager telephonyManager;
    LinearLayout linearLayoutContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verify_account);
        iniatilizeView();
        initializeButton();
        hideKeyboardFunction();
    }

    private void iniatilizeView(){
        emailEditText = (EditText) findViewById(R.id.edit_email);
        passEditText = (EditText) findViewById(R.id.edit_passID);
        mobileEditText = (EditText) findViewById(R.id.edit_mobile);
        verifyButton = (Button) findViewById(R.id.btn_account_verify);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.click);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        linearLayoutContext = (LinearLayout) findViewById(R.id.content_verify_account);
    }

    private void initializeButton(){
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                vibrator.vibrate(50);
                mp.start();
                String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
                String email = emailEditText.getText().toString();
                String pass = passEditText.getText().toString();
                String mobile = mobileEditText.getText().toString();

                SharedPreferences sharedPreferences = VerifyAccount.this.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.EMAIL, email);
                editor.putString(Constants.PASS_ID, pass);
                editor.putString(Constants.MOBILE, mobile);
                //editor.putString(Constants.ANDROID_ID, android_id);
                editor.apply();

                new ProcessVerifyAccount(VerifyAccount.this, emailEditText,passEditText,mobileEditText,
                        Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID)).execute(email,pass,mobile,android_id);

            }
        });
    }

    private void hideKeyboardFunction(){
        linearLayoutContext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
