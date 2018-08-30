package com.example.jim.superferry.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jim.superferry.Process.ProcessGetSchedule;
import com.example.jim.superferry.R;
import com.example.jim.superferry.adapter.VesselSchedAdapter;

/*import com.example.jim.superferry.adapter.VesselScheduleAdapter;
import com.example.jim.superferry.others.CardView;*/


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SeatAvailable.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SeatAvailable#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VesselSchedFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Button btnSeat;
    private Spinner stationOrigSpinner;
    private Spinner stationDestSpinner;
    private String mParam1;
    private String mParam2;

    private RecyclerView schedRecycleViewer;
/*    private VesselScheduleAdapter mAdapter;
    private ArrayList<CardView> cardViewsCollection;*/

     VesselSchedAdapter vesselSchedAdapter;

    ListView listView;

    private OnFragmentInteractionListener mListener;



    public VesselSchedFragment() {

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
        return inflater.inflate(R.layout.layout_recycler, container, false);

    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {

        super.onViewCreated(view,savedInstanceState);


        initializeView(view);
        vesselSchedAdapter = new VesselSchedAdapter(getActivity(), R.layout.adapter_view_layout);
        listView.setAdapter(vesselSchedAdapter);

        new ProcessGetSchedule(getActivity(), vesselSchedAdapter, listView).execute();



    }

    private void initializeView(View view){
        listView =(ListView) view.findViewById(R.id.listView);

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
