package edu.calvin.cs262.shuffleboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
 * Fragment that will hold the Static and Dynamic EventList views
 */
public class EventListFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    View me;
    String DB_BASE = new GlobalVariables().DB_BASE;
    ArrayList<String> staticEventIDs = new ArrayList<String>();
    ArrayList<String> dynamicEventIDs = new ArrayList<String>();

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
        me = myView;

        //Set adapter with the string array of events from saved data, input them into list
        //Change depending on list
        if (mPage == 1) {
            new GetStaticEvents().execute();
        } else {
            new GetDynamicEvents().execute();
        }

        return myView;
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
                String[] eventslist = result.split("___");
                String[] events;
                ArrayList<String> myEvents = new ArrayList<String>();
                StaticEvent newEvent;
                for (int i = 0; i < eventslist.length; i++) {
                    events = eventslist[i].split("__");
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    try {
                        newEvent = new StaticEvent(Integer.parseInt(events[2]), Integer.parseInt(events[3]), events[1], 1, days);
                        //TODO add the new event to the listview
                        myEvents.add(newEvent.toString());
                        //myEvents.add(events[1] + "\n");
                    } catch (Exception e) {

                    }
                }

                ListView eventList = (ListView) me.findViewById(R.id.listView);
                ArrayAdapter<String> myAdapter=new
                        ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        myEvents);
                eventList.setAdapter(myAdapter);

            } else result = "uhoh";
        }

    }

    private class GetDynamicEvents extends AsyncTask<Void, Void, String> {

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
                String[] eventslist = result.split("___");
                String[] events;
                ArrayList<String> myEvents = new ArrayList<String>();
                DynamicEvent newEvent;
                for (int i = 0; i < eventslist.length; i++) {
                    events = eventslist[i].split("__");
                    //TODO make sure this is right
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    newEvent = new DynamicEvent(Integer.parseInt(events[2]), Double.parseDouble(events[3]), events[1], 1);
                    //TODO add the new event to the listview
                    myEvents.add(newEvent.toString());
                    dynamicEventIDs.add(events[0]);
                }

                ListView eventList = (ListView) me.findViewById(R.id.listView);
                ArrayAdapter<String> myAdapter=new
                        ArrayAdapter<String>(
                        getContext(),
                        R.layout.event_item,
                        myEvents);
                eventList.setAdapter(myAdapter);

                eventList.setClickable(true);
                eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                        ScheduleFragment sched = (ScheduleFragment) getParentFragment();
//                        sched.editDynamic(Integer.parseInt(dynamicEventIDs.get(position)), (String) arg0.getItemAtPosition(position));

                        String eventInfo = (String) arg0.getItemAtPosition(position);
                        EventDynamicCreate frag = new EventDynamicCreate();

                        //Bundle the event info
                        String[] eventInfoArray = eventInfo.split("\n");
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", Integer.parseInt(dynamicEventIDs.get(position)));
                        bundle.putString("times", eventInfoArray[2].split(" ")[1]);
                        bundle.putString("duration", eventInfoArray[1].split(" ")[1]);
                        bundle.putString("name", eventInfoArray[0]);
                        frag.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                        // Replace whatever is in the flContent view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.flContent, frag);
                        transaction.addToBackStack("back");

                        // Commit the transaction
                        transaction.commit();
                        getActivity().setTitle("Edit Flexible Event");
                       /* int eventID = Integer.parseInt(dynamicEventIDs.get(position));
                        EventDynamicCreate frag = new EventDynamicCreate();

                        //Bundle the event info
                        String eventInfo = (String) arg0.getItemAtPosition(position);
                        String[] eventInfoArray = eventInfo.split("\n");
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", eventID);
                        bundle.putString("times", eventInfoArray[2].split(" ")[1]);
                        bundle.putString("duration", eventInfoArray[1].split(" ")[1]);
                        bundle.putString("name", eventInfoArray[0]);
                        frag.setArguments(bundle);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                        // Replace whatever is in the flContent view with this fragment,
                        // and add the transaction to the back stack so the user can navigate back
                        transaction.replace(R.id.flContent, frag);
                        transaction.addToBackStack("back");

                        // Commit the transaction
                        transaction.commit();*/
                    }
                });

            } else result = "uhoh";
        }

    }

}
