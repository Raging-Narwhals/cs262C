package edu.calvin.cs262.shuffleboard;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * Defines the UI for the static events list view
 */
public class EventViewStatic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button createStaticEventButton;
    Button goToDynamicEventButton;

    //Hard-coded data for demo, delete once data creation and storing is implemented
    boolean[] temp1days = {false,true,false,true,false,true,false};
    StaticEvent temp1 = new StaticEvent(32, 34, "Soccer Practice", 0, temp1days);
    boolean[] temp2days = {false,false,true,false,false,false,false};
    StaticEvent temp2 = new StaticEvent(38, 42, "Unicycle Club", 0, temp2days);
    boolean[] temp3days = {false,true,false,false,true,false,false};
    StaticEvent temp3 = new StaticEvent(22, 25, "Tutoring", 0, temp3days);

    String[] temps = {temp1.toString(), temp2.toString(), temp3.toString()};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventViewStatic.
     */
    // TODO: Rename and change types and number of parameters
    public static EventViewStatic newInstance(String param1, String param2) {
        EventViewStatic fragment = new EventViewStatic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventViewStatic() {
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
        final View myView = inflater.inflate(R.layout.fragment_event_view_static, container, false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) myView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getFragmentManager(),
                getContext()));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        ListView eventList = (ListView) myView.findViewById(R.id.listView2);

        //align button object with UI buttons
        createStaticEventButton = (Button) myView.findViewById(R.id.createStaticEventButton);
        goToDynamicEventButton = (Button) myView.findViewById(R.id.goToDynamicEventsButton);

        //when button is clicked, send user to UI page for creating static events
        createStaticEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of ExampleFragment
                EventStaticCreate frag = new EventStaticCreate();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, frag);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        //when button is clicked, send user to UI page for viewing static events
        goToDynamicEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of ExampleFragment
                EventViewDynamic frag = new EventViewDynamic();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, frag);
                //transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });

        //Set adapter with the string array of events from saved data, input them into list
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                temps);
        eventList.setAdapter(myAdapter);

        return myView;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
