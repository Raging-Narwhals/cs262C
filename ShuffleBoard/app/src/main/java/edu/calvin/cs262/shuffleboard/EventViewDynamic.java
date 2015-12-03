package edu.calvin.cs262.shuffleboard;

import android.net.Uri;
import android.os.AsyncTask;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


/**
 * Defines the UI for the dynamic events list view
 */
public class EventViewDynamic extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    View me;
    static String DB_BASE = "http://153.106.116.66:9998/shuffle/";

    Button createDynamicEventButton;
    Button goToStaticEventButton;


    //Hard-coded data for demo, delete once data creation and storing is implemented
    DynamicEvent temp1 = new DynamicEvent(2, 1, "Grading", 0);
    DynamicEvent temp2 = new DynamicEvent(3, 0.5, "Work Out", 0);
    DynamicEvent temp3 = new DynamicEvent(1, 1.5, "Slay Bears", 0);

    String[] temps = {temp1.toString(), temp2.toString(), temp3.toString()};

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventViewDynamic.
     */
    // TODO: Rename and change types and number of parameters
    public static EventViewDynamic newInstance(String param1, String param2) {
        EventViewDynamic fragment = new EventViewDynamic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventViewDynamic() {
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
        final View myView = inflater.inflate(R.layout.fragment_event_view_dynamic, container, false);
        me = myView;
/*
        ListView eventList = (ListView) myView.findViewById(R.id.calendarEventListView);

       //align button object with UI buttons
        createDynamicEventButton = (Button) myView.findViewById(R.id.createDynamicEventButton);
        goToStaticEventButton = (Button) myView.findViewById(R.id.goToStaticEventsButton);

        //when button is clicked, send user to UI page for creating a dynamic event
        createDynamicEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of Fragment
                EventDynamicCreate frag = new EventDynamicCreate();

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the flContent view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.flContent, frag);
                transaction.addToBackStack(null);

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
        eventList.setAdapter(myAdapter);*/

        new GetEvents().execute();

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

    private class GetEvents extends AsyncTask<Void, Void, String> {

        private final String USERNAME_URI = DB_BASE + "user/";
        String result;

        /**
         * This method extracts text from the HTTP response entity.
         *
         * @param entity
         * @return
         * @throws IllegalStateException
         * @throws IOException
         */
        protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
            InputStream in = entity.getContent();
            StringBuffer out = new StringBuffer();
            int n = 1;
            while (n > 0) {
                byte[] b = new byte[4096];
                n = in.read(b);
                if (n > 0) out.append(new String(b, 0, n));
            }
            return out.toString();
        }

        /**
         * This method issues the HTTP GET request.
         *
         * @param params
         * @return
         */
        @Override
        protected String doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpGet httpGet = new HttpGet(USERNAME_URI + "1/events/dynamic");
            String text = null;
            try {
                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity entity = response.getEntity();
                text = getASCIIContentFromEntity(entity);
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            return text;
        }

        /**
         * The method runs before the others.
         */
        protected void onPreExecute() {
        }

        /**
         * The method takes the results of the request, when they arrive, and updates the interface.
         *
         * @param results
         */
        protected void onPostExecute(String results) {
            if (results != null) {
                //       id  event st  sp  days
                //result=
                result = results;
                String[] eventslist = result.split("|||");
                String[] events;
                ArrayList<DynamicEvent> myEvents = new ArrayList<DynamicEvent>();
                DynamicEvent newEvent;
                for (int i = 0; i < eventslist.length; i++) {
                    events = eventslist[i].split("||");
                    //TODO make sure this is right
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    newEvent = new DynamicEvent(Integer.getInteger(events[2]), Double.parseDouble(events[3]), events[1], 1);
                    //TODO add the new event to the listview
                }


                /*ListView eventList = (ListView) getView().findViewById(R.id.staticEventListView);
                String[] friends = result.split(",");
                //Set adapter with the string array of events from saved data, input them into list
                ArrayAdapter<String> myAdapter=new
                        ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        friends);
                eventList.setAdapter(myAdapter);*/
            } else result = "uhoh";
        }

    }

}
