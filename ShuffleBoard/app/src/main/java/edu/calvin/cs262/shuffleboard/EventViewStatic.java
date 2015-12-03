package edu.calvin.cs262.shuffleboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
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

    View me;
    static String DB_BASE = "http://153.106.116.66:9998/shuffle/";

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
        me = myView;

        new GetStaticEvents().execute();
/*
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class GetStaticEvents extends AsyncTask<Void, Void, String> {

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
            HttpGet httpGet = new HttpGet(USERNAME_URI + "1/events/static");
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
                //result=1||Lunch||28||32||1000100|||...
                result = results;
                String[] eventslist = result.split("|||");
                String[] events;
                ArrayList<StaticEvent> myEvents = new ArrayList<StaticEvent>();
                StaticEvent newEvent;
                for (int i = 0; i < eventslist.length; i++) {
                    events = eventslist[i].split("||");
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    newEvent = new StaticEvent(Integer.getInteger(events[2]), Integer.getInteger(events[3]), events[1], 1, days);
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
