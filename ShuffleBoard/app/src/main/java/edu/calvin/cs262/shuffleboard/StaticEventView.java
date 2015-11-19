package edu.calvin.cs262.shuffleboard;


/**
 * Class corresponding to the UI page for viewing static events; schedule view
 *
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

public class StaticEventView extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_event_view);

        ListView eventList = (ListView) findViewById(R.id.listView2);

        //align button object with UI buttons
        createStaticEventButton = (Button) findViewById(R.id.createStaticEventButton);
        goToDynamicEventButton = (Button) findViewById(R.id.goToDynamicEventsButton);

        //when button is clicked, send user to UI page for creating static events
        createStaticEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), CreateStaticEvent.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //when button is clicked, send user to UI page for viewing dynamic events
        goToDynamicEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), DynamicEventView.class);
                startActivityForResult(myIntent, 0);
            }
        });

        //Set adapter with the string array of events from saved data, input them into list
        ArrayAdapter<String> myAdapter=new
                ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                temps);
        eventList.setAdapter(myAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_static_event_view, menu);
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