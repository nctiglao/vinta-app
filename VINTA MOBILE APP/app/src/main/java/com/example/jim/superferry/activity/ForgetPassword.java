package com.example.jim.superferry.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jim.superferry.R;

/**
 * Created by jim on 06/06/2018.
 */

public class ForgetPassword extends Activity {

   // EditText email;
    EditText username;
    Button btnconfirm;
    TextView textView;
    Vibrator vibrator;
    MediaPlayer mp;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forget_form);
        // email = (EditText) findViewById(R.id.emailedit);
        textView = (TextView) findViewById(R.id.texttxt);
        linearLayout = (LinearLayout) findViewById(R.id.linearContent);
        username = (EditText) findViewById(R.id.usernamedit);
        btnconfirm = (Button) findViewById(R.id.btnconfirm2);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.click);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                mp.start();
                Intent intent = new Intent(ForgetPassword.this, VerifyCode.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                ForgetPassword.this.startActivity(intent);
                ForgetPassword.this.finish();
                System.exit(0);

            }
        });
    }

/*        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                mp.start();
                String user = username.getText().toString();
                new ProcessForgetPass(ForgetPassword.this, username).execute(user, Constants.FORGET_PASSWORD);
            }
        });
    }*/
    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
