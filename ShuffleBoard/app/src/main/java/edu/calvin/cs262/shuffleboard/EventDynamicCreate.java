package edu.calvin.cs262.shuffleboard;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Vector;


/**
 * Defines the UI and logic for the creation of new events
 */
public class EventDynamicCreate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    DynamicEvent newEvent;
    Button createButton;
    EditText name, duration, timesPerWeek;
    CheckBox[] dayCheckBoxes = new CheckBox[7];
    Vector<Triple> dayTimes = new Vector<Triple>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDynamicCreate.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDynamicCreate newInstance(String param1, String param2) {
        EventDynamicCreate fragment = new EventDynamicCreate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventDynamicCreate() {
        // Required empty public constructor
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
        View myView = inflater.inflate(R.layout.fragment_event_dynamic_create, container, false);

        // Define variables to access the editText objects
        name = (EditText) myView.findViewById(R.id.name);
        duration = (EditText) myView.findViewById(R.id.duration);
        timesPerWeek = (EditText) myView.findViewById(R.id.timesPerWeek);

        // Define variables to access the day checkboxes
        dayCheckBoxes[0] = (CheckBox) myView.findViewById(R.id.SundayCheckBox);
        dayCheckBoxes[1] = (CheckBox) myView.findViewById(R.id.MondayCheckBox);
        dayCheckBoxes[2] = (CheckBox) myView.findViewById(R.id.TuesdayCheckBox);
        dayCheckBoxes[3] = (CheckBox) myView.findViewById(R.id.WednesdayCheckBox);
        dayCheckBoxes[4] = (CheckBox) myView.findViewById(R.id.ThursdayCheckBox);
        dayCheckBoxes[5] = (CheckBox) myView.findViewById(R.id.FridayCheckBox);
        dayCheckBoxes[6] = (CheckBox) myView.findViewById(R.id.SaturdayCheckBox);

        // Create button
        createButton = (Button) myView.findViewById(R.id.create);
        // onClick for the create button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durationNum = Integer.valueOf(duration.getText().toString());
                int timesPerWeekNum = Integer.valueOf(timesPerWeek.getText().toString());
                for (int i=0; i < 7; i++) {
                    if (dayCheckBoxes[i].isChecked()) {
                        dayTimes.add(new Triple(i, 0, 47));
                    }
                }

                newEvent = new DynamicEvent(timesPerWeekNum, durationNum, name.getText().toString(), 0, dayTimes);

                // Go back to the StaticEvent fragment
                EventViewDynamic frag = new EventViewDynamic();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the flContent view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.flContent, frag);
                //transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
