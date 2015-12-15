package edu.calvin.cs262.shuffleboard;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
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

    View me;
    String DB_BASE = new GlobalVariables().DB_BASE;

    int eventID = -1;
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
        me = myView;

        //If editing an existing event, the eventID will be set here
        eventID = getArguments().getInt("id");

        // Define variables to access the editText objects
        name = (EditText) myView.findViewById(R.id.name);
        duration = (EditText) myView.findViewById(R.id.duration);
        timesPerWeek = (EditText) myView.findViewById(R.id.timesPerWeek);

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
        if (eventID==-1) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int durationNum = Integer.valueOf(duration.getText().toString());
                    int timesPerWeekNum = Integer.valueOf(timesPerWeek.getText().toString());
                    for (int i = 0; i < 7; i++) {
                        if (dayCheckBoxes[i].isChecked()) {
                            dayTimes.add(new Triple(i, 0, 47));
                        }
                    }

                    newEvent = new DynamicEvent(timesPerWeekNum, durationNum, name.getText().toString(), 0, dayTimes);

                    new CreateDynamicEvent().execute();

                    // Go back to the StaticEvent fragment
                    ScheduleFragment frag = new ScheduleFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    // Replace whatever is in the flContent view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.flContent, frag);
                    //transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();

                    // also supports Toast.LENGTH_LONG
                    Toast.makeText(getContext(), "Dynamic Event created", Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            String eventTimes = getArguments().getString("times");
            timesPerWeek.setText(eventTimes);
            String eventDuration = getArguments().getString("duration");
            duration.setText(eventDuration);
            String eventName = getArguments().getString("name");
            name.setText(eventName);

            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Float durationNum = Float.valueOf(duration.getText().toString());
                    int timesPerWeekNum = Integer.valueOf(timesPerWeek.getText().toString());
                    for (int i = 0; i < 7; i++) {
                        if (dayCheckBoxes[i].isChecked()) {
                            dayTimes.add(new Triple(i, 0, 47));
                        }
                    }

                    newEvent = new DynamicEvent(timesPerWeekNum, durationNum, name.getText().toString(), 0, dayTimes);

                    new EditDynamicEvent().execute();

                    // Go back to the StaticEvent fragment
                    ScheduleFragment frag = new ScheduleFragment();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    // Replace whatever is in the flContent view with this fragment,
                    // and add the transaction to the back stack so the user can navigate back
                    transaction.replace(R.id.flContent, frag);
                    //transaction.addToBackStack(null);
                    // Commit the transaction
                    transaction.commit();

                    // also supports Toast.LENGTH_LONG
                    Toast.makeText(getContext(), "Dynamic Event edited", Toast.LENGTH_SHORT).show();
                }
            });
        }

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

    private class CreateDynamicEvent extends AsyncTask<Void, Void, String> {

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
            HttpPut httpPut = new HttpPut(USERNAME_URI + "1/events/dynamic/addLong/" + newEvent.toDB());
            String text = null;
            try {
                httpPut.setEntity(new StringEntity(newEvent.toString()));
                HttpResponse response = httpClient.execute(httpPut, localContext);
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
            } else result = "uhoh";
        }

    }

    private class EditDynamicEvent extends AsyncTask<Void, Void, String> {

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
            HttpPut httpPut = new HttpPut(USERNAME_URI + "1/events/dynamic/edit/" + eventID + "/" + newEvent.toDB());
            String text = null;
            try {
                httpPut.setEntity(new StringEntity(newEvent.toString()));
                HttpResponse response = httpClient.execute(httpPut, localContext);
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
            } else result = "uhoh";
        }

    }
}
