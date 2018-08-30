package com.example.jim.superferry.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessAccountDetails;
import com.example.jim.superferry.Process.ProcessLogout;
import com.example.jim.superferry.R;
import com.example.jim.superferry.others.BottomNavigationViewHelper;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jim on 18/04/2018.
 */

public class AccountDetails extends AppCompatActivity {

    private TextView click;
    private TextView user_name;
    private TextView firstname;
    private TextView lastname;
    private CircleImageView user_profile;
    SharedPreferences sp;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_my_account);
        click = (TextView) findViewById(R.id.click);
        firstname = (TextView) findViewById(R.id.AccFnametxtView);
        lastname = (TextView) findViewById(R.id.AccLnametxtView);
        user_profile = (CircleImageView) findViewById(R.id.user_profile);
        click.setPaintFlags(click.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);


        Toolbar toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Account");



        /*sp = getSharedPreferences(Constants.SHARED_PREFERENCES2, Context.MODE_PRIVATE);
            sp.getString("userPic","");
            sp.getString("userFname","");
            sp.getString("userLname","");
        if (sp.contains("userPic") && sp.contains("userFname") && sp.contains("userLname")){
            byte[] decodeString = Base64.decode(sp.getString("userPic",""), Base64.DEFAULT);
            Bitmap decoded = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);

            firstname.setText(sp.getString("userFname",""));
            lastname.setText(sp.getString("userLname",""));
            ((CircleImageView) findViewById(R.id.user_profile)).setImageBitmap(decoded);

        }else {*/
            account_details();
       // }





        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountDetails.this);

                builder.setTitle("View Points");
                builder.setMessage("You got 30 point/s ");
                builder.setCancelable(true);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent0 = new Intent(AccountDetails.this, ActionMain.class);
                      //  intent0.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        AccountDetails.this.startActivity(intent0);
                        finish();
                        break;

                    case R.id.nav_vesselETA:
                        Intent intent1 = new Intent(AccountDetails.this, TicketScanActivity.class);
                      //  intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        AccountDetails.this.startActivity(intent1);
                        finish();

                        break;

                    case R.id.nav_bottomAccount:


                        break;
                    case R.id.nav_about:
                        Intent intent3 = new Intent(AccountDetails.this, AboutActivity.class);
                   //     intent3.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        AccountDetails.this.startActivity(intent3);
                        finish();
                        break;


                }

                return false;
            }
        });
    }


    private void account_details(){
  /*      String pref_data = Constants.SESSION_USER;
        new ProcessAccountDetails(AccountDetails.this, Constants.SESSION_USER).execute(pref_data, Constants.GET_PROFILE);*/

        SharedPreferences sharedPreferences = this.getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);


        String pref_data = sharedPreferences.getString(Constants.SESSION_USER, null);
        new ProcessAccountDetails(AccountDetails.this, sharedPreferences.getString(Constants.SESSION_USER, null)).execute(pref_data, Constants.GET_PROFILE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(AccountDetails.this);
                builder.setTitle("Account");
                builder.setMessage("Do you really want to log out?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
/*                        String pref_data = Constants.SESSION_USER;

                        new ProcessLogout(AccountDetails.this ,Constants.SESSION_USER).execute(pref_data, Constants.LOGOUT_CONNECTION);*/

                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE);


                        String pref_data = sharedPreferences.getString(Constants.SESSION_USER, null);
                        //new ProcessAccountDetails(AccountDetails.this, sharedPreferences.getString(Constants.SESSION_USER, null)).execute(pref_data, Constants.GET_PROFILE);
                        new ProcessLogout(AccountDetails.this ,sharedPreferences.getString(Constants.SESSION_USER, null)).execute(pref_data, Constants.LOGOUT_CONNECTION);

                    }


                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
