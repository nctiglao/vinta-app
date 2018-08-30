package com.example.jim.superferry.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jim.superferry.R;

/**
 * Created by jim on 07/06/2018.
 */

public class ChangePass extends Activity {

    EditText newpass;
    EditText renewpass;
    Button btnconfirm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_forgetpass);

        newpass = (EditText) findViewById(R.id.newpass);
        renewpass = (EditText) findViewById(R.id.renewpass);
        btnconfirm = (Button) findViewById(R.id.btnconfirm);


        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newpass.getText().toString().equals(renewpass.getText().toString())){


                }else{
                    Toast.makeText(ChangePass.this, "Input not matched.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
