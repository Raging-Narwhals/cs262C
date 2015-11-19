package edu.calvin.cs262.shuffleboard;

import android.app.Activity;
import android.content.Intent;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventStaticCreate.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventStaticCreate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventStaticCreate extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    StaticEvent newEvent;
    Button createButton;
    EditText name, startTime, endTime;
    CheckBox[] dayCheckBoxes = new CheckBox[7];

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventStaticCreate.
     */
    // TODO: Rename and change types and number of parameters
    public static EventStaticCreate newInstance(String param1, String param2) {
        EventStaticCreate fragment = new EventStaticCreate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventStaticCreate() {
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
        View myView = inflater.inflate(R.layout.fragment_event_static_create, container, false);

        // Define variables to access the editText objects
        name = (EditText) myView.findViewById(R.id.name);
        startTime = (EditText) myView.findViewById(R.id.startTime);
        endTime = (EditText) myView.findViewById(R.id.endTime);

        // Define variables to access the day checkboxes
        dayCheckBoxes[0] = (CheckBox) myView.findViewById(R.id.sundayCheckBox);
        dayCheckBoxes[1] = (CheckBox) myView.findViewById(R.id.mondayCheckBox);
        dayCheckBoxes[2] = (CheckBox) myView.findViewById(R.id.tuesdayCheckBox);
        dayCheckBoxes[3] = (CheckBox) myView.findViewById(R.id.wednesdayCheckBox);
        dayCheckBoxes[4] = (CheckBox) myView.findViewById(R.id.thursdayCheckBox);
        dayCheckBoxes[5] = (CheckBox) myView.findViewById(R.id.fridayCheckBox);
        dayCheckBoxes[6] = (CheckBox) myView.findViewById(R.id.saturdayCheckBox);

        // Create button
        createButton = (Button) myView.findViewById(R.id.create);
        // onClick for the create button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] start = startTime.getText().toString().split(":");
                String[] end = endTime.getText().toString().split(":");
                int startVal = (Integer.valueOf(start[1]) >= 15) ? ((Integer.valueOf(start[1]) < 45) ? 1 : 2) : 0;
                int endVal = (Integer.valueOf(end[1]) >= 15) ? ((Integer.valueOf(end[1]) < 45) ? 1 : 2) : 0;
                int startNum = 2*Integer.valueOf(start[0]) + startVal;
                int endNum = 2*Integer.valueOf(end[0]) + endVal;
                if (validate(startNum, endNum))
                {
                    boolean[] setDays = new boolean[7];
                    for (int i=0; i < 7; i++) {
                        setDays[i] = dayCheckBoxes[i].isChecked();
                    }

                    newEvent = new StaticEvent(startNum, endNum, name.getText().toString(),
                            0, setDays);
                }

                // Go back to the StaticEvent fragment
                EventViewStatic frag = new EventViewStatic();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, frag);
                //transaction.addToBackStack(null);
                // Commit the transaction
                transaction.commit();
            }
        });

        return myView;
    }

    /* Validates a time slot
 * @param startNum: an integer value 0-46 representing the beginning of the event
 * @param endNum: an integer value 1-47 representing the end of the event
 */
    public boolean validate(int startNum, int endNum) {
        if (name.getText()!=name.getHint())
        {
            if (0 <= startNum && startNum < endNum && endNum <= 47)
                return true;
        }
        return false;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

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
