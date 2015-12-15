package edu.calvin.cs262.shuffleboard;

import android.os.AsyncTask;

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
 * Created by JStay on 12/15/2015.
 */
public class CustomCalendar {

    String DB_BASE = new GlobalVariables().DB_BASE;

    ArrayList<StaticEvent> staticEvents;
    ArrayList<String> staticEventIDs;
    ArrayList<DynamicEvent> dynamicEvents;
    ArrayList<String> dynamicEventIDs;



    public CustomCalendar() {
        staticEvents = new ArrayList<StaticEvent>();
        staticEventIDs = new ArrayList<String>();
        dynamicEvents = new ArrayList<DynamicEvent>();
        dynamicEventIDs = new ArrayList<String>();
        getStatics();
        getDynamics();
    }

    public String getShuffle() {
        String retString = "";
        for (int i = 0; i < staticEvents.size(); i++) {
            retString += staticEvents.get(i).toDB() + "___";
        }
        return retString;
    }

    public void getStatics() {
        new GetStaticEvents().execute();
        System.out.println("\nGetting static events\n");
    }

    public void getDynamics() {
        new GetDynamicEvents().execute();
        System.out.println("\nGetting dynamic events\n");
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
                result = results;
                String[] eventslist = result.split("___");
                for (int i = 0; i < eventslist.length; i++) {
                    String[] events = eventslist[i].split("__");
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    try {
                        staticEvents.add(new StaticEvent(Integer.parseInt(events[2]), Integer.parseInt(events[3]), events[1], 1, days));
                        staticEventIDs.add(events[0]);
                    } catch (Exception e) {

                    }
                }
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
                result = results;
                String[] eventslist = result.split("___");
                for (int i = 0; i < eventslist.length; i++) {
                    String[] events = eventslist[i].split("__");
                    //TODO make sure this is right
                    boolean[] days = {events[4].charAt(0)=='1', events[4].charAt(1)=='1', events[4].charAt(2)=='1', events[4].charAt(3)=='1', events[4].charAt(4)=='1',
                            events[4].charAt(5)=='1', events[4].charAt(6)=='1'};
                    //TODO add the new event to the listview
                    dynamicEvents.add(new DynamicEvent(Integer.parseInt(events[2]), Double.parseDouble(events[3]), events[1], 1));
                    dynamicEventIDs.add(events[0]);
                }
            } else result = "uhoh";
        }

    }

}
