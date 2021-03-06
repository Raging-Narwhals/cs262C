package edu.calvin.cs262.shuffleboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ScheduleFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ScheduleFragment() {
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
        View myView = inflater.inflate(R.layout.fragment_schedule, container, false);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) myView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new EventsPagerAdapter
                (getChildFragmentManager()));


        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) myView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) myView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    EventStaticCreate frag = new EventStaticCreate();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", -1);
                    frag.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the flContent view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.flContent, frag);
                    transaction.addToBackStack("back");

                    // Commit the transaction
                    transaction.commit();
                    getActivity().setTitle("New Fixed Event");
                } else {
                    EventDynamicCreate frag = new EventDynamicCreate();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", -1);
                    frag.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();

                    // Replace whatever is in the flContent view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.flContent, frag);
                    transaction.addToBackStack("back");

                    // Commit the transaction
                    transaction.commit();
                    getActivity().setTitle("New Flexible Event");
                }


            }
        });

        // setup FAB click listener

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

    public void editStatic(int id, String eventInfo) {
        EventStaticCreate frag = new EventStaticCreate();

        //Bundle the event info
        String[] eventInfoArray = eventInfo.split("\n");
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        String[] times = eventInfoArray[1].split(" ");
        String start = times[0].substring(0, times[0].length() - 2);
        start = (times[0].contains("AM") ? start.split(":")[0] : Integer.toString(Integer.parseInt(start.split(":")[0]) + 12)) + ":" + start.split(":")[1];
        String stop = times[2].substring(0,times[2].length()-2);
        stop = (times[2].contains("AM") ? stop.split(":")[0] : Integer.toString(Integer.parseInt(stop.split(":")[0]) + 12)) + ":" + stop.split(":")[1];
        bundle.putString("start", start);
        bundle.putString("stop", stop);
        bundle.putString("name", eventInfoArray[0]);
        frag.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);
        transaction.addToBackStack("back");

        // Commit the transaction
        transaction.commit();
    }

    public void editDynamic(int id, String eventInfo) {
        EventDynamicCreate frag = new EventDynamicCreate();

        //Bundle the event info
        String[] eventInfoArray = eventInfo.split("\n");
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("times", eventInfoArray[2].split(" ")[3]);
        bundle.putString("duration", eventInfoArray[1].split(" ")[1]);
        bundle.putString("name", eventInfoArray[0]);
        frag.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);
        transaction.addToBackStack("back");

        // Commit the transaction
        transaction.commit();
    }
}
