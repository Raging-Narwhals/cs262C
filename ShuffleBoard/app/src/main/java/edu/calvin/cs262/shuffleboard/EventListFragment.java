package edu.calvin.cs262.shuffleboard;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by user on 12/3/2015.
 */
public class EventListFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    //Hard-coded data for demo, delete once data creation and storing is implemented
    boolean[] temp1days = {false,true,false,true,false,true,false};
    StaticEvent staticTemp1 = new StaticEvent(32, 34, "Soccer Practice", 0, temp1days);
    boolean[] temp2days = {false,false,true,false,false,false,false};
    StaticEvent staticTemp2 = new StaticEvent(38, 42, "Unicycle Club", 0, temp2days);
    boolean[] temp3days = {false,true,false,false,true,false,false};
    StaticEvent staticTemp3 = new StaticEvent(22, 25, "Tutoring", 0, temp3days);

    String[] staticTemps = {staticTemp1.toString(), staticTemp2.toString(), staticTemp3.toString()};

    DynamicEvent dynamicTemp1 = new DynamicEvent(2, 1, "Grading", 0);
    DynamicEvent dynamicTemp2 = new DynamicEvent(3, 0.5, "Work Out", 0);
    DynamicEvent dynamicTemp3 = new DynamicEvent(1, 1.5, "Slay Bears", 0);

    String[] dynamicTemps = {dynamicTemp1.toString(), dynamicTemp2.toString(), dynamicTemp3.toString()};


    public static EventListFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        EventListFragment fragment = new EventListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

    }

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.fragment_event_list, container, false);

        ListView eventList = (ListView) myView.findViewById(R.id.listView);

        //Set adapter with the string array of events from saved data, input them into list
        //Change depending on list
        if (mPage == 1) {
            ArrayAdapter<String> myAdapter=new
                    ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    staticTemps);
            eventList.setAdapter(myAdapter);
        } else {
            ArrayAdapter<String> myAdapter=new
                    ArrayAdapter<String>(
                    getContext(),
                    android.R.layout.simple_list_item_1,
                    dynamicTemps);
            eventList.setAdapter(myAdapter);
        }


        return myView;
    }
}
