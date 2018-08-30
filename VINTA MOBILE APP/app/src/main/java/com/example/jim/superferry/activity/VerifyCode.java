package com.example.jim.superferry.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessVerifyCode;
import com.example.jim.superferry.R;

/**
 * Created by jim on 07/06/2018.
 */

public class VerifyCode extends Activity {

    EditText usernameEdit;
    EditText codeEdit;
    EditText newpass1;
    EditText renewpass1;
    Button btnVer;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_code);

        usernameEdit = (EditText) findViewById(R.id.UsernameEdit);
        codeEdit = (EditText) findViewById(R.id.codeEdit);
        newpass1 = (EditText) findViewById(R.id.newpass1);
        renewpass1 = (EditText) findViewById(R.id.renewpass1);
        btnVer = (Button) findViewById(R.id.btnconfirm_ver);
        linearLayout = (LinearLayout) findViewById(R.id.linearContent);

        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                hideKeyboard(view);
                return false;
            }
        });

        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setTitle("Notice");
        builder.setMessage("Please be inform that verification code was sent to your email! \n Thank you!");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (newpass1.getText().toString().equals(renewpass1.getText().toString())) {
                    String username = usernameEdit.getText().toString();
                    String code = codeEdit.getText().toString();
                    String newpass = newpass1.getText().toString();
                    new ProcessVerifyCode(VerifyCode.this,usernameEdit ,codeEdit,newpass1).execute(username,code,newpass, Constants.CONFIRM_PASS);
                }else{
                    Toast.makeText(VerifyCode.this, "Password doesn't match.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
