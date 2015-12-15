package edu.calvin.cs262.shuffleboard;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

public class CalendarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RelativeLayout[] week = new RelativeLayout[7];

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarFragment() {
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
        View myView = inflater.inflate(R.layout.fragment_calendar, container, false);

        final ScrollView scroll = (ScrollView) myView.findViewById(R.id.calendarScrollView);
        Handler h = new Handler();

        h.postDelayed(new Runnable() {

            @Override
            public void run() {
                scroll.scrollTo(0, 720);
            }
        }, 250); // 250 ms delay


        week[0] = (RelativeLayout) myView.findViewById(R.id.sundayRelativeLayout);
        week[1] = (RelativeLayout) myView.findViewById(R.id.mondayRelativeLayout);
        week[2] = (RelativeLayout) myView.findViewById(R.id.tuesdayRelativeLayout);
        week[3] = (RelativeLayout) myView.findViewById(R.id.wednesdayRelativeLayout);
        week[4] = (RelativeLayout) myView.findViewById(R.id.thursdayRelativeLayout);
        week[5] = (RelativeLayout) myView.findViewById(R.id.fridayRelativeLayout);
        week[6] = (RelativeLayout) myView.findViewById(R.id.saturdayRelativeLayout);

        String results = getArguments().getString("results");
        String resultStatic = results.split("_______")[0];
        String[] eventslist = resultStatic.split("___");
        ArrayList<String> myEventsStatic = new ArrayList<String>();
        for (int i = 0; i < eventslist.length; i++) {
            String[] events = eventslist[i].split("__");
            boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                    events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
            try {
                myEventsStatic.add(new StaticEvent(Integer.parseInt(events[2]), Integer.parseInt(events[3]), events[1], 1, days).toDB());
            } catch (Exception e) {

            }
        }

        String resultDynamic = results.split("_______")[1];
        eventslist = resultDynamic.split("___");
        ArrayList<String> myEventsDynamic = new ArrayList<String>();
        for (int i = 0; i < eventslist.length; i++) {
            String[] events = eventslist[i].split("__");
            boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                    events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
            try {
                myEventsDynamic.add(new DynamicEvent(Integer.parseInt(events[2]), Double.parseDouble(events[3]), events[1], 1).toDB());
            } catch (Exception e) {

            }
        }

        shuffle(myEventsStatic, myEventsDynamic);

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

    public void shuffle(ArrayList<String> myEventsStatic, ArrayList<String> myEventsDynamic) {
        for (int i = 0; i < myEventsStatic.size(); i++) {
            String[] event = myEventsStatic.get(i).split("__");
            for (int j = 0; j < 7; j++) {
                if (event[2].charAt(j) == '1') {
                    EditText newText = new EditText(getContext());
                    newText.setMaxLines(1);
                    newText.setSingleLine();
                    newText.setTextSize(20);
                    newText.setText(event[3].replace("%20", " "));
                    newText.setBackgroundColor(Color.RED);
                    newText.setClickable(false);
                    newText.setFocusable(false);
                    newText.setCursorVisible(false);
                    newText.setPadding(0,5,0,5);
                    int length = 30*(Integer.parseInt(event[1])-Integer.parseInt(event[0]));
                    int offset = 30*Integer.parseInt(event[0]);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(60, length);
                    lp.setMargins(0, offset, 0, 0);
                    newText.setLayoutParams(lp);
                    week[j].addView(newText);
                }
            }
        }

        for (int i = 0; i < myEventsDynamic.size(); i++) {
            String[] event = myEventsDynamic.get(i).split("__");
            for (int j = 0; j < 7; j++) {
                if (event[2].charAt(j) == '1') {
                    EditText newText = new EditText(getContext());
                    newText.setMaxLines(1);
                    newText.setText(event[3].replace("%20", " "));
                    newText.setBackgroundColor(Color.BLUE);
                    newText.setClickable(false);
                    newText.setFocusable(false);
                    newText.setCursorVisible(false);
                    newText.setPadding(0,5,0,5);
                    int length = 30*Integer.parseInt(event[1]);
                    int offset = 30*Integer.parseInt(event[0]);
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(60, length);
                    lp.setMargins(0, offset, 0, 0);
                    newText.setLayoutParams(lp);
                    week[j].addView(newText);
                }
            }
        }
    }

}
