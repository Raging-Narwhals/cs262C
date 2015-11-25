package edu.calvin.cs262.shuffleboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple UI for the user to search for other users in order to request calendar sharing
 */
public class AddPerson extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String[] temps = {"Bob", "Dr J 307", "Dr Whack-a-mole", "Isaac"};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPerson.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPerson newInstance(String param1, String param2) {
        AddPerson fragment = new AddPerson();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AddPerson() {
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
        View myView = inflater.inflate(R.layout.fragment_add_person, container, false);

        ListView eventList = (ListView) myView.findViewById(R.id.suggestList);

        //Set adapter with the string array of events from saved data, input them into list
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                temps);
        eventList.setAdapter(myAdapter);

        eventList.setClickable(true);
        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                //Object o = arg0.getItemAtPosition(position);

                String name = (String) arg0.getItemAtPosition(position);
                new AlertDialog.Builder(getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Send request to share?")
                        .setMessage("Send request to share with user \"" + name + "\"?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Create an instance of Fragment
                                EventViewDynamic frag = new EventViewDynamic();

                                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                // Replace whatever is in the fragment_container view with this fragment,
                                // and add the transaction to the back stack so the user can navigate back
                                transaction.replace(R.id.fragment_container, frag);

                                // Commit the transaction
                                transaction.commit();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

/*        //EditText name = (EditText) myView.findViewById(R.id.name);
        Button searchPeopleButton = (Button) myView.findViewById(R.id.goToStaticEventsButton);

        //when button is clicked, send user to UI page for creating a dynamic event
        searchPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) v.findViewById(R.id.name);
                //TODO make this work with the database
                String myName = name.getText().toString();
                List<String> old = new ArrayList<String>();
                for (int i=0; i < temps.length; i++) {
                    old.add(temps[i]);
                }
                old.add(myName);
            }
        });*/

        return myView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
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
