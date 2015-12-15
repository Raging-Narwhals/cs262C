package edu.calvin.cs262.shuffleboard;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoadingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoadingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    String DB_BASE = new GlobalVariables().DB_BASE;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoadingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadingFragment newInstance(String param1, String param2) {
        LoadingFragment fragment = new LoadingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LoadingFragment() {
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

        new GetAllEvents().execute();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loading, container, false);
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

    private class GetAllEvents extends AsyncTask<Void, Void, String> {

        private final String USERNAME_URI = DB_BASE + "user/";

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
            HttpGet httpGetStatic = new HttpGet(USERNAME_URI + "1/events/static");
            String textStatic = null;
            String textDynamic = null;
            try {
                HttpResponse responseStatic = httpClient.execute(httpGetStatic, localContext);
                HttpEntity entityStatic = responseStatic.getEntity();
                textStatic = getASCIIContentFromEntity(entityStatic);
                HttpGet httpGetDynamic = new HttpGet(USERNAME_URI + "1/events/dynamic");
                try {
                    HttpResponse responseDynamic = httpClient.execute(httpGetDynamic, localContext);
                    HttpEntity entityDynamic = responseDynamic.getEntity();
                    textDynamic = getASCIIContentFromEntity(entityDynamic);

                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            } catch (Exception e) {
                return e.getLocalizedMessage();
            }
            return textStatic + "____" + textDynamic;
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
                String resultStatic = results.split("_______")[0];
                String[] eventslist = resultStatic.split("___");
                ArrayList<String> myEvents = new ArrayList<String>();
                for (int i = 0; i < eventslist.length; i++) {
                    String[] events = eventslist[i].split("__");
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    try {
                        myEvents.add(new StaticEvent(Integer.parseInt(events[2]), Integer.parseInt(events[3]), events[1], 1, days).toDB());
                    } catch (Exception e) {

                    }
                }

                String resultDynamic = results.split("_______")[1];
                eventslist = resultDynamic.split("___");
                for (int i = 0; i < eventslist.length; i++) {
                    String[] events = eventslist[i].split("__");
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    try {
                        myEvents.add(new DynamicEvent(Integer.parseInt(events[2]), Double.parseDouble(events[3]), events[1], 1).toDB());
                    } catch (Exception e) {

                    }
                }

                // TODO Call Shuffle here
                CalendarFragment frag = new CalendarFragment();
                Bundle bundle = new Bundle();
                bundle.putString("results", results);
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

    }

}
