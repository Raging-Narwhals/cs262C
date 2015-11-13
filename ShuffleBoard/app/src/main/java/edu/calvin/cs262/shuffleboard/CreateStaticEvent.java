package edu.calvin.cs262.shuffleboard;

/** CreateStaticEvent defines the methods involved with validating and creating new static events
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

public class CreateStaticEvent extends AppCompatActivity {

    StaticEvent newEvent;
    Button createButton;
    EditText name, startTime, endTime;
    CheckBox[] dayCheckBoxes = new CheckBox[7];
    //String[] days = {"sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_static_event);

        // Define variables to access the editText objects
        name = (EditText) findViewById(R.id.name);
        startTime = (EditText) findViewById(R.id.startTime);
        endTime = (EditText) findViewById(R.id.endTime);

        // Define variables to access the day checkboxes
        dayCheckBoxes[0] = (CheckBox) findViewById(R.id.sundayCheckBox);
        dayCheckBoxes[1] = (CheckBox) findViewById(R.id.mondayCheckBox);
        dayCheckBoxes[2] = (CheckBox) findViewById(R.id.tuesdayCheckBox);
        dayCheckBoxes[3] = (CheckBox) findViewById(R.id.wednesdayCheckBox);
        dayCheckBoxes[4] = (CheckBox) findViewById(R.id.thursdayCheckBox);
        dayCheckBoxes[5] = (CheckBox) findViewById(R.id.fridayCheckBox);
        dayCheckBoxes[6] = (CheckBox) findViewById(R.id.saturdayCheckBox);

        // Create button
        createButton = (Button) findViewById(R.id.create);
        // onClick for the create button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] start = startTime.getText().toString().split(":");
                String[] end = endTime.getText().toString().split(":");
                int startVal = (Integer.valueOf(start[1]) >= 15) ? ((Integer.valueOf(start[1]) < 45) ? 1 : 2) : 0;
                int endVal = (Integer.valueOf(end[1]) >= 15) ? ((Integer.valueOf(end[1]) < 45) ? 1 : 2) : 0;
                int startNum = 2*Integer.valueOf(start[0]) + startVal;
                int endNum = 2*Integer.valueOf(end[0]) + endVal;
                if (validate(startNum, endNum))
                {
                    boolean[] setDays = new boolean[7];
                    for (int i=0; i < 7; i++) {
                        setDays[i] = dayCheckBoxes[i].isChecked();
                    }

                    newEvent = new StaticEvent(startNum, endNum, name.getText().toString(),
                            0, setDays);
                }

                // Return back to the schedule view of dynamic events
                Intent myIntent = new Intent(v.getContext(), StaticEventView.class);
                startActivityForResult(myIntent, 0);
            }
        });
    }

    /* Validates a time slot
     * @param startNum: an integer value 0-46 representing the beginning of the event
     * @param endNum: an integer value 1-47 representing the end of the event
     */
    public boolean validate(int startNum, int endNum) {
        if (name.getText()!=name.getHint())
        {
            if (0 <= startNum && startNum < endNum && endNum <= 47)
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_create_static_event, menu);
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
