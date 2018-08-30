package com.example.jim.superferry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessAccountCreate;
import com.example.jim.superferry.R;
import com.example.jim.superferry.others.HttpUrl;

/**
 * Created by jim on 16/03/2018.
 */

public class AccountActivateCreate extends Activity {
    HttpUrl httpUrl = new HttpUrl();
    public EditText edit_text_username, edit_text_password, edit_c_pass;
    public TextView text_app_name1;
    public TextView textViewTimer;
    public ImageView img_app_logo, img_close_current_profile;
    public RelativeLayout.LayoutParams r_l_app_logo_,r_l_app_name;
    public RelativeLayout r_l_load_img;
    public View content_main, progress_load_profile_img;
    public Button btn_login;
    LinearLayout linearLayout;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public CheckBox checkBox;
    public TextView txtFrgtPass;
    public String iD;
    CountDownTimer sessionTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        img_app_logo = (ImageView) findViewById(R.id.img_app_logo);
        text_app_name1 = (TextView) findViewById(R.id.text_app_name1);
        edit_text_username = (EditText) findViewById(R.id.edit_text_username);
        textViewTimer = (TextView) findViewById(R.id.textViewTimer);
        edit_text_password = (EditText)findViewById(R.id.edit_text_password);
        btn_login = (Button) findViewById(R.id.btnLogin);
        checkBox = (CheckBox) findViewById(R.id.cbShwPwd2);
        txtFrgtPass = (TextView) findViewById(R.id.txtFrgtPass);
        linearLayout = (LinearLayout) findViewById(R.id.content_main);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked){
                    //Show Password
                    edit_text_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    //Hide Password
                    edit_text_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });




        Toast.makeText(this, Constants.CODE, Toast.LENGTH_LONG).show();

        sessionTimer = new CountDownTimer(Constants.SESSION_TIMER * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                textViewTimer.setText( ""+millisUntilFinished / 1000);
            }

            public void onFinish() {
                Intent intent = new Intent(AccountActivateCreate.this, AccountActivateValidate.class);
                AccountActivateCreate.this.startActivity(intent);
                AccountActivateCreate.this.finish();
            }
        }.start();
        setLogin();



    }


    public void setLogin(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edit_text_username.getText().toString();
                String password = edit_text_password.getText().toString();
                String id = Constants.CODE;
                new ProcessAccountCreate(AccountActivateCreate.this,sessionTimer, edit_text_username, edit_text_password, Constants.CODE).execute(username, password, id, Constants.ACTIVATE_CREATE_CONNECTION);
            }
        });

    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
