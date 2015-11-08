package edu.calvin.cs262.shuffleboard;

/**
 * Class corresponding to the UI page for viewing dynamic events; schedule view
 *
 * Created by Spencer Schultz on 11/6/2015.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class DynamicEventView extends AppCompatActivity {

    Button createDynamicEventButton;
    Button goToStaticEventButton;


    //Hard-coded data for demo, delete once data creation and storing is implemented
    String tempEvent1 = "Grading \nDuration: 1 Hour \nTimes Per Week: 2";
    String tempEvent2 = "Work out \nDuration: 0.5 Hour \nTimes Per Week: 3";
    String tempEvent3 = "Slay Bears \nDuration: 1.5 Hour \nTimes Per Week: 1";
    String[] tempEvents = {tempEvent1, tempEvent2, tempEvent3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_event_view);

        ListView listView = (ListView) findViewById(R.id.listView);

        //align button object with UI buttons
        createDynamicEventButton = (Button) findViewById(R.id.createDynamicEventButton);
        goToStaticEventButton = (Button) findViewById(R.id.goToStaticEventsButton);

        //when button is clicked, send user to UI page for creating a dynamic event
        createDynamicEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CreateDynamicEvent.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //when button is clicked, send user to UI page for viewing static events
        goToStaticEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), StaticEventView.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Set adapter with the string array of events from saved data, input them into list
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                tempEvents);
        listView.setAdapter(myAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dynamic_event_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
