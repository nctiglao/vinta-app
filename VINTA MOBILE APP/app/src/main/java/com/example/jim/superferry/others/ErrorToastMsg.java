package com.example.jim.superferry.others;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;

/**
 * Created by jim on 16/03/2018.
 */

public class ErrorToastMsg {
    private Toast toast = null;

    public ErrorToastMsg(final Activity activity, final EditText editText, final String errorMsg, final String forced){

        if(forced.matches("yes")){
            DisplayToastMsg(activity, editText, errorMsg);
        }

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP && editText.getCompoundDrawables()[2] != null){
                    if (motionEvent.getRawX() >= editText.getRight() - editText.getTotalPaddingRight()){
                        DisplayToastMsg(activity,editText,errorMsg);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    public void DisplayToastMsg(Activity activity, EditText editText, String errorMsg){
        if (toast != null){
            toast.cancel();
        }
        if(!errorMsg.matches("")){
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View toast_layout = inflater.inflate(R.layout.layout_toast,
                    (ViewGroup) (activity).findViewById(R.id.toast_layout_root));
            TextView toast_msg = (TextView)toast_layout.findViewById(R.id.txt_toast_msg);
            toast =  new Toast(activity);
            toast_msg.setText(String.valueOf(errorMsg));
            toast.setDuration(Toast.LENGTH_LONG);
            toast.show();

            toast.setGravity(Gravity.END | Gravity.TOP, editText.getLeft() + 50, editText.getTop() + Constants.offSetTop);
            toast.setView(toast_layout);
            toast.show();
        }
    }
}
