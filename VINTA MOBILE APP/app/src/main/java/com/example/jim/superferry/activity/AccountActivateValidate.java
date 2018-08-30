package com.example.jim.superferry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.GetSessionKey;
import com.example.jim.superferry.Process.ProcessAccountValidate;
import com.example.jim.superferry.R;
import com.example.jim.superferry.others.Holder;
import com.example.jim.superferry.others.HttpUrl;


/**
 * Created by jim on 16/03/2018.
 */

public class AccountActivateValidate extends Activity {
    HttpUrl httpUrl = new HttpUrl();
    public ImageView imgShowHidePass;
    public View vBorer1;
    public TextView textCreateAccount, textContentMessage, textShowHidePass, textShowInfo;
    public EditText editCreateAccountName, editCreateAccountContact, editCreateAccountUser, editCreateAccountPass , editLastName, editActivationCode, editBirthday;
    public Button btnCreateAccount;
    Holder holder = new Holder();
    LinearLayout linearLayoutContext;

    String passShowHide = "show";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionKey();

        setContentView(R.layout.create_account_activities);


        vBorer1 = findViewById(R.id.vBorder_1);

        //ImageView
        textCreateAccount = (TextView)findViewById(R.id.text_create_account);
        textContentMessage = (TextView)findViewById(R.id.text_content_message);
        //EditText
        editCreateAccountName = (EditText)findViewById(R.id.edit_c_a_name);
        editLastName = (EditText)findViewById(R.id.edit_c_a_lname);
        editBirthday = (EditText)findViewById(R.id.birthday);
        editActivationCode = (EditText)findViewById(R.id.edit_c_a_activation_code);
        btnCreateAccount = (Button) findViewById(R.id.btn_account_create);
        linearLayoutContext = (LinearLayout) findViewById(R.id.content_main_create_customer_account);

        linearLayoutContext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });


        createAccount();


    }




    public void showHidePassword(){
        textShowHidePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passShowHide.matches("show")){
                    editCreateAccountPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_FILTER);
                    textShowHidePass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_hide, 0, 0,0);
                    textShowHidePass.setText(String.valueOf("HIDE"));
                    passShowHide="hide";
                }
                else {
                    editCreateAccountPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textShowHidePass.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_show, 0,0,0);
                    textShowHidePass.setText(String.valueOf("SHOW"));
                    passShowHide= "show";


                }
                editCreateAccountPass.setTypeface(editCreateAccountUser.getTypeface());
                editCreateAccountPass.setSelection(editCreateAccountPass.getText().length());
            }
        });
    }

    public void createAccount(){
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Holder holder = new Holder();


                String fname =editCreateAccountName.getText().toString();
                String lname = editLastName.getText().toString();
                String dob = editBirthday.getText().toString();
                String code = editActivationCode.getText().toString();
                new ProcessAccountValidate(AccountActivateValidate.this, editCreateAccountContact, editLastName, editBirthday,editActivationCode ).execute(fname, lname, dob, code,Constants.ACTIVATE_VALIDATE_CONNECTION);

            }

        });


    }


    @Override
    public void onBackPressed() {
        Intent intent1 = new Intent(AccountActivateValidate.this, LoginActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        AccountActivateValidate.this.startActivity(intent1);
        finish();

        super.onBackPressed();
    }

    public void sessionKey(){
            String code = Constants.CODE;
        new GetSessionKey(AccountActivateValidate.this, Constants.CODE).execute(code, Constants.ACTIVATED);
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
