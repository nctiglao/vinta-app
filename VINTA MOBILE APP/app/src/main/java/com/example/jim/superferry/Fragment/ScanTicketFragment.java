package com.example.jim.superferry.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jim.superferry.activity.ActivityMain;
import com.example.jim.superferry.Constants;
import com.example.jim.superferry.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by jim on 06/04/2018.
 */

public class ScanTicketFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView zXingScannerView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    TextView textView;
    TextView textButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);
        initializeView(view);
        scan(view);
    }

    private void initializeView(View view){

        textView = (TextView)view.findViewById(R.id.scanResult);
        textButton = (TextView)view.findViewById(R.id.btnProceed);

    }

    public void scan (View view) {


        zXingScannerView = new ZXingScannerView(getActivity().getApplicationContext());
        getActivity().setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();


    }



    @Override
    public void handleResult(Result rawResult) {
        Constants.SCAN_RESULT = rawResult.getText();
        Toast.makeText(getActivity(), Constants.SCAN_RESULT, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), ActivityMain.class);
        this.startActivity(intent);
        getActivity().finish();
        zXingScannerView.stopCamera();
    }
}
