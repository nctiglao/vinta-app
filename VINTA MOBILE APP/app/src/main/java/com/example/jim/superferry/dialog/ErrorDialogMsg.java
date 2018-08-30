package com.example.jim.superferry.dialog;

import android.app.Activity;
import android.app.AlertDialog;

/**
 * Created by jim on 16/03/2018.
 */

public class ErrorDialogMsg {
    public ErrorDialogMsg(Activity activity, String errMsg){
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        alert.setMessage(errMsg);
        alert.setPositiveButton("OK", null);
        alert.show();
    }
}
