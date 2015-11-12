package edu.calvin.cs262.shuffleboard;

/** CreateDynamicEvent defines the methods involved with validating and creating new dynamic events
 * to be added to the schedule.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Vector;

public class CreateDynamicEvent extends AppCompatActivity {

    DynamicEvent newEvent;
    Button createButton;
    EditText name, duration, timesPerWeek;
    CheckBox[] dayCheckBoxes = new CheckBox[7];
    Vector<Triple> dayTimes = new Vector<Triple>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dynamic_event);

        // Define variables to access the editText objects
        name = (EditText) findViewById(R.id.name);
        duration = (EditText) findViewById(R.id.duration);
        timesPerWeek = (EditText) findViewById(R.id.timesPerWeek);

        // Define variables to access the day checkboxes
        dayCheckBoxes[0] = (CheckBox) findViewById(R.id.SundayCheckBox);
        dayCheckBoxes[1] = (CheckBox) findViewById(R.id.MondayCheckBox);
        dayCheckBoxes[2] = (CheckBox) findViewById(R.id.TuesdayCheckBox);
        dayCheckBoxes[3] = (CheckBox) findViewById(R.id.WednesdayCheckBox);
        dayCheckBoxes[4] = (CheckBox) findViewById(R.id.ThursdayCheckBox);
        dayCheckBoxes[5] = (CheckBox) findViewById(R.id.FridayCheckBox);
        dayCheckBoxes[6] = (CheckBox) findViewById(R.id.SaturdayCheckBox);

        // Create button
        createButton = (Button) findViewById(R.id.create);
        // onClick for the create button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int durationNum = Integer.valueOf(duration.getText().toString());
                int timesPerWeekNum = Integer.valueOf(timesPerWeek.getText().toString());
                for (int i=0; i < 7; i++) {
                    if (dayCheckBoxes[i].isChecked()) {
                        dayTimes.add(new Triple(i, 0, 47));
                    }
                }

                newEvent = new DynamicEvent(timesPerWeekNum, durationNum, name.getText().toString(), 0, dayTimes);

                // Return back to the schedule view of dynamic events
                Intent myIntent = new Intent(v.getContext(), DynamicEventView.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_dynamic_event, menu);
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
