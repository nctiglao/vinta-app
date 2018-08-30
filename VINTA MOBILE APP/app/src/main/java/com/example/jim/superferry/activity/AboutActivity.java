package com.example.jim.superferry.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.R;
import com.example.jim.superferry.others.BottomNavigationViewHelper;

/**
 * Created by jim on 23/04/2018.
 */

public class AboutActivity extends Activity {

    TextView feedback;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_about);

        feedback = (TextView) findViewById(R.id.feedback);

        feedback.setPaintFlags(feedback.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AboutActivity.this);
                builder.setCancelable(false);
                builder.setTitle("Feedback");
                builder.setMessage("Tell us what you think.");
                builder.setIcon(R.drawable.ic_feedback_black_24dp);

                View alertDialogView = AboutActivity.this.getLayoutInflater().inflate(R.layout.layout_feedbox, null);
                final EditText feedbox = (EditText) alertDialogView.findViewById(R.id.feedboxTxtView);
                builder.setView(alertDialogView);


                builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Toast.makeText(AboutActivity.this, feedbox.getText().toString(), Toast.LENGTH_LONG).show();

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                 builder.create().show();
            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navView_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);






        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        Intent intent0 = new Intent(AboutActivity.this, ActionMain.class);
                     //   intent0.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        AboutActivity.this.startActivity(intent0);
                        finish();
                        break;

                    case R.id.nav_vesselETA:
                        Intent intent1 = new Intent(AboutActivity.this, TicketScanActivity.class);
                    //    intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        AboutActivity.this.startActivity(intent1);
                        finish();

                        break;

                    case R.id.nav_bottomAccount:
                        Intent intent = new Intent(AboutActivity.this, LoginActivity.class);
                   //     intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        AboutActivity.this.startActivity(intent);
                        finish();

                        break;

                    case R.id.nav_about:

                        break;

                }

                return false;
            }
        });

    }
}
