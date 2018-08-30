package com.example.jim.superferry.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessForgotSign;
import com.example.jim.superferry.Process.ProcessLogin;
import com.example.jim.superferry.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Created by jim on 20/03/2018.
 */

public class LoginActivity extends Activity {
    public EditText editTextUsername;
    public EditText editTextPassword;
    public CheckBox checkBox;
    LinearLayout linearLayout;
    Vibrator vibrator;
    MediaPlayer mp;
    final public static int REQUEST_CAMERA = 100;
    TelephonyManager telephonyManager;


    Button btnLogin;
    TextView activate_login, forgot_pass;
    SharedPreferences sp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initialize Content
        setContentView(R.layout.login_account_activity);
        linearLayout = (LinearLayout) findViewById(R.id.linearContent);
        editTextUsername = (EditText)findViewById(R.id.edit_text_login_username);
        editTextPassword = (EditText)findViewById(R.id.edit_text_login_password);
        activate_login = (TextView)findViewById(R.id.activate_login_code);
        btnLogin = (Button)findViewById(R.id.btn_Login);
        checkBox = (CheckBox)findViewById(R.id.cbShwPwd);
        forgot_pass = (TextView)findViewById(R.id.txtFrgtPass);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        mp = MediaPlayer.create(this, R.raw.click);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

//        String uId = telephonyManager.getImei();


        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideKeyboard(view);
                return false;
            }
        });

       // editeTextHide();
        //   setLoginform();

        validateAccount();
        //Check PREF DATA
        sp = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
           sp.getString(Constants.SESSION_USER, "");

        if(sp.contains(Constants.SESSION_USER)){
            Intent intent = new Intent(this, AccountDetails.class);
            this.startActivity(intent);
            this.finish();
        }


        initializeForgot();

        loginSetup();

        checkCameraPermission();
        googleServicesAvailable();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked){
                    //Show Password
                    editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    //Hide Password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }

            }
        });



      //  initializeAndroidID();
      //  initializeForgot();

    }


    private void validateAccount(){
        activate_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                mp.start();
                Intent intent = new Intent(LoginActivity.this, VerifyAccount.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }

    private void loginSetup(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(50);
                mp.start();
                String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                new ProcessLogin(LoginActivity.this,editTextUsername, editTextPassword,
                        Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID)).execute(username, password, android_id,Constants.LOGIN_CONNECTION);
            }
        });
            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.USER_NAME, editTextUsername.toString().toString());
            editor.apply();

    }


    public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.CAMERA},
                        Constants.REQUEST_PERMISSION_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.CAMERA},
                        Constants.REQUEST_PERMISSION_CAMERA);
            }
            return false;

        } else
            return false;
    }
    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(this, "Can't connect to Play Services", Toast.LENGTH_LONG).show();
            finish();
        }
        return false;
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case Constants.REQUEST_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {

                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Camera");
                    builder.setMessage("You need to enable Camera permission to continue.");
                    builder.setCancelable(true);

                    builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = null;
                            if (android.os.Build.VERSION.SDK_INT >= 26) {
                                intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);

                                Uri uri = Uri.fromParts("package", LoginActivity.this.getPackageName(), null);
                                intent.setData(uri);
                                startActivityForResult(intent, Constants.REQUEST_PERMISSION_CAMERA);
                            }
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                } break;


        }

    }

    @Override
    public void onBackPressed() {

        Intent intent1 = new Intent(LoginActivity.this, TicketScanActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        LoginActivity.this.startActivity(intent1);
        finish();

        super.onBackPressed();
    }


    public void hideKeyboard(View view){
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    private void initializeForgot(){

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editTextUsername.getText().toString().isEmpty() || editTextUsername.getText().toString().equals("")){

                    Toast.makeText(LoginActivity.this, "Please input your Email Address.", Toast.LENGTH_SHORT).show();
                }else{

                    String email = editTextUsername.getText().toString();
                    String android_id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);


                    new ProcessForgotSign(LoginActivity.this, editTextUsername,
                            Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID)).execute(email, android_id);

                }




            }
        });
    }



}
