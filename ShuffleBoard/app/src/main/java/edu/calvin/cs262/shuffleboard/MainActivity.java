package edu.calvin.cs262.shuffleboard;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.setDrawerListener(drawerToggle);

        // Check whether the activity is using the layout version with
        // the flContent FrameLayout. If so, we must add the first fragment
        if (findViewById(R.id.flContent) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of Fragment
            ScheduleFragment frag = new ScheduleFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            frag.setArguments(getIntent().getExtras());

            // Add the fragment to the 'flContent' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.flContent, frag).commit();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToCalendar(MenuItem item) {
        // Create an instance of Fragment
        CalendarFragment frag = new CalendarFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);

        // Commit the transaction
        transaction.commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    public void goToSchedule(MenuItem item) {
        // Create an instance of Fragment
        ScheduleFragment frag = new ScheduleFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);

        // Commit the transaction
        transaction.commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    public void goToFriends(MenuItem item) {
        // Create an instance of Fragment
        Friends frag = new Friends();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);

        // Commit the transaction
        transaction.commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    public void goToRequests(MenuItem item) {
        // Create an instance of Fragment
        Requests frag = new Requests();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);

        // Commit the transaction
        transaction.commit();
        item.setChecked(true);
        setTitle(item.getTitle());
        mDrawer.closeDrawers();
    }

    public void editDynamic(int id, String eventInfo) {
        EventDynamicCreate frag = new EventDynamicCreate();

        //Bundle the event info
        String[] eventInfoArray = eventInfo.split("\n");
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("times", eventInfoArray[2].split(" ")[1]);
        bundle.putString("duration", eventInfoArray[1].split(" ")[1]);
        bundle.putString("name", eventInfoArray[0]);
        frag.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the flContent view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.flContent, frag);
        transaction.addToBackStack("back");

        // Commit the transaction
        transaction.commit();
        setTitle("Edit Flexible Event");
    }

    public void goToHelp(MenuItem item) {
        Uri webpage = Uri.parse("http://github.com/Raging-Narwhals/cs262C/wiki/FAQ");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Do extra stuff here

        setTitle(R.string.app_name);
    }
}
