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
import android.widget.TextView;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessResendVerification;
import com.example.jim.superferry.Process.ProcessSendVerification;
import com.example.jim.superferry.R;

/**
 * Created by jim on 21/08/2018.
 */

public class SendVerify extends Activity {

    EditText codeEdit;
    TextView resendTxt;
    Button button;
    Vibrator vibrator;
    MediaPlayer mp;
    TelephonyManager telephonyManager;
    LinearLayout linearLayoutContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_verfy_code);

        initializeView();
        initializeButton();
        initializeResend();
        hideKeyboardFunction();
    }


    private void initializeView(){
        codeEdit = (EditText) findViewById(R.id.verify_code);
        resendTxt = (TextView) findViewById(R.id.resendText);
        button = (Button) findViewById(R.id.submitButton);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.click);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        linearLayoutContext = (LinearLayout) findViewById(R.id.content_code_verify);

    }

    private void initializeButton(){

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // editor.putString("sessionUser", sessionUser);


                vibrator.vibrate(50);
                mp.start();
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String email = sharedPreferences.getString(Constants.EMAIL, null);
                String pass_id = sharedPreferences.getString(Constants.PASS_ID, null);
                String mobile = sharedPreferences.getString(Constants.MOBILE, null);
                String code = codeEdit.getText().toString();
                String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
                new ProcessSendVerification(SendVerify.this,sharedPreferences.getString(Constants.EMAIL, null),sharedPreferences.getString(Constants.PASS_ID, null),
                        sharedPreferences.getString(Constants.MOBILE, null),
                        Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID),
                        codeEdit).execute(email,pass_id,mobile,android_id ,code);
            }
        });


    }

    private void initializeResend(){
        resendTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                mp.start();

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
                String email = sharedPreferences.getString(Constants.EMAIL, null);
                String pass_id = sharedPreferences.getString(Constants.PASS_ID, null);
                String mobile = sharedPreferences.getString(Constants.MOBILE, null);
                String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

                new ProcessResendVerification(SendVerify.this,sharedPreferences.getString(Constants.EMAIL, null),sharedPreferences.getString(Constants.PASS_ID, null),
                        sharedPreferences.getString(Constants.MOBILE, null),
                        Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID)).execute(email,pass_id,mobile, android_id);

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
