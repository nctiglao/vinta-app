package com.example.jim.superferry.Fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jim.superferry.Constants;
import com.example.jim.superferry.Process.ProcessSeatAvailability;
import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.SeatAvailListAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeatAvailable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeatAvailable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeatAvailable extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnSeat;
    private Spinner stationOrigSpinner;
    private Spinner stationDestSpinner;
    Vibrator vibrator;
    MediaPlayer mp;
    SeatAvailListAdapter seatAvailListAdapter;
    ListView listView;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;



    public SeatAvailable() {

    }


    public static SeatAvailable newInstance(String param1, String param2) {
        SeatAvailable fragment = new SeatAvailable();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


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
        return inflater.inflate(R.layout.fragment_seat_available, container, false);
    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);

        initializeView(view);
        initializeSpinner();
        initializeOriginSpinner();
        initializeDestSpinner();
        initializeSpinner();
        initializeOnClickListeners();
    }

    private void initializeView(View view){

        stationOrigSpinner = (Spinner) view.findViewById(R.id.stationOrigSpinner);
        stationDestSpinner = (Spinner) view.findViewById(R.id.stationDestSpinner);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        btnSeat = (Button) view.findViewById(R.id.btnSeat);
         mp = MediaPlayer.create(getActivity(), R.raw.click);

    }

    private void initializeOnClickListeners(){
        btnSeat.setOnClickListener(viewSeatAvailableOnClickListener);
    }
    private View.OnClickListener viewSeatAvailableOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mp.start();
            vibrator.vibrate(50);
            String station_origin = Constants.STATION_ORIGIN;
            String station_dest = Constants.STATION_DEST;
            String station_origin2 = Constants.STATION_ORIGIN2;
            String station_dest2 = Constants.STATION_DEST2;
                new ProcessSeatAvailability(getActivity(),Constants.STATION_ORIGIN, Constants.STATION_DEST, Constants.STATION_ORIGIN2,Constants.STATION_DEST2, seatAvailListAdapter).execute(station_origin, station_dest,station_origin2,station_origin2, Constants.CHKSEAT_CONNECTION);


        }
    };



    private void initializeOriginSpinner(){

        final ArrayAdapter<String> originArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item){


            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if (position == getCount()) {

                    textView.setText("");
                    textView.setHint(getItem(getCount()));

                }
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);

                return view;
            }
            @Override
            public int getCount() {

                return super.getCount() - 1;

            }
        };

        originArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originArrayAdapter.clear();

        for (String vesseltype : Constants.STATION){
            originArrayAdapter.add(vesseltype);
        }
        originArrayAdapter.add("Select Station");
        stationOrigSpinner.setAdapter(originArrayAdapter);
        stationOrigSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Constants.STATION_ORIGIN = "PLAMEX";
                        break;
                    case 1:
                        Constants.STATION_ORIGIN = "MOA";
                        break;

                    case 2:
                        Constants.STATION_ORIGIN = "NVLT";
                        break;

                }
              Constants.STATION_ORIGIN2 = stationOrigSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stationOrigSpinner.setSelection(originArrayAdapter.getCount());
    }

    private void initializeDestSpinner(){

        final ArrayAdapter<String> destArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item){


            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                if (position == getCount()) {

                    textView.setText("");
                    textView.setHint(getItem(getCount()));

                }
                textView.setGravity(Gravity.CENTER);
                textView.setTextSize(20);

                return view;
            }
            @Override
            public int getCount() {

                return super.getCount() - 1;

            }
        };

        destArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destArrayAdapter.clear();

        for (String vesseltype : Constants.STATION){
            destArrayAdapter.add(vesseltype);
        }
        destArrayAdapter.add("Select Station");
        stationDestSpinner.setAdapter(destArrayAdapter);
        stationDestSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             switch (position){
                 case 0:
                     Constants.STATION_DEST = "PLAMEX";
                     break;
                 case 1:
                     Constants.STATION_DEST = "MOA";
                     break;
                 case 2:
                     Constants.STATION_DEST = "NVLT";
                     break;

             }

            Constants.STATION_DEST2 = stationDestSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        stationDestSpinner.setSelection(destArrayAdapter.getCount());
    }


 private void initializeSpinner() {

     final ArrayAdapter<String> TimesTypeArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item) {


         @Override
         public View getView(int position, View convertView, ViewGroup parent) {

             View view = super.getView(position, convertView, parent);

             TextView textView = (TextView) view.findViewById(android.R.id.text1);

             if (position == getCount()) {

                 textView.setText("");
                 textView.setHint(getItem(getCount()));

             }

             textView.setGravity(Gravity.CENTER);
             textView.setTextSize(18);

             return view;
         }

         @Override
         public int getCount() {

             return super.getCount() - 1;

         }

     };

     TimesTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
     TimesTypeArrayAdapter.clear();
 }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
