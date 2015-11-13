package edu.calvin.cs262.shuffleboard;

import java.util.Vector;

/**
 * Defines the DynamicEvent class
 *   Events that are not set and can be shuffled
 *
 */
public class DynamicEvent {

    int timesPerWeek;  // How many times this event should happen this week
    double length;  // How long the event is
    String name;  // Short phrase describing the events
    int ownerID;  // The id of the owner in the database
    Vector<Triple> days;
    String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public DynamicEvent() {
        timesPerWeek = ownerID = 0;
        length = 0;
        days = new Vector<Triple>(0);
    }

    public DynamicEvent(int numTimes, double duration, String newName, int owner) {
        timesPerWeek = numTimes;
        length = duration;
        name = newName;
        ownerID = owner;
    }

    public DynamicEvent(int numTimes, double duration, String newName, int owner, Vector<Triple> day) {
        timesPerWeek = numTimes;
        length = duration;
        name = newName;
        ownerID = owner;
        days = day;
    }

    /*
     * Getters and setters
     */

    // Return the id of the owner
    public int getOwnerID() {
        return ownerID;
    }

    // Check if the owner exists, and then set the owner
    public int setOwnerID(int owner) {
        // TODO must check that owner exists in database?
        ownerID = owner;
        return ownerID;
    }

    // Return the boolean[] that specifies the days the event is on
    public Vector<Triple> getDays() {
        return days;
    }

    // Set the days that the event is on
    public Vector<Triple> setDays(Vector<Triple> day) throws Exception {
        if (day.size() == 0) {
            throw new EventException("DynamicEvent setDays(): Cannot set using empty day vector<Triple>");
        } else {
            for (int i = 0; i < day.size(); i++) {
                days.add(day.elementAt(i));
            }
        }
        return days;
    }

    // Set whether an event is scheduled for a specific day
    public Triple addDayTime(Triple day) {
        // Make sure event is not already scheduled for this specified time slot
        for (int i = 0; i < days.size(); i++) {
            if ( day.within(days.elementAt(i))) {
                return days.elementAt(i);
            }
        }
        // If we get here, we want to add the event
        days.add(day);
        return day;
    }

    // Remove an event time slot from a specified day
    public Triple removeDayTime(Triple day) throws Exception {
        boolean isRemoved = false;
        Triple removed = new Triple();
        // Find the event in the list and remove it
        for (int i = 0; i < days.size(); i++) {
            if (days.elementAt(i).within(day)) {
                removed = days.elementAt(i);
                days.remove(i);
                isRemoved = true;
                break;
            }
        }

        // If we removed, return the removed time slot
        if (isRemoved) {
            return removed;
        } else {
            // Else, alert the user that we did not find the time to be removed
            throw new EventException("Triple removeDayTime(): Event not found for this time");
        }
    }

    // Edit an existing event's time slot for a specified day
    // If an error, throw an exception
    public Triple editDayTime(Triple day, int newDay, int newStart, int newStop) {
        Triple original = day;
        Boolean exception = false;
        try {
            day.setDay(newDay);
            day.setStart(newStart);
            day.setStop(newStop);
        } catch (Exception e) {
            exception = true;
            e.printStackTrace();
        }

        if (exception) {
            return original;
        } else {
            return day;
        }
    }

    public String toString() {
        String ret = name + "\n";
        ret += "Duration: " + Double.toString(length) + (length==1 ? " hour\n" : " hours\n");
        ret += "Times Per Week: " + Integer.toString(timesPerWeek);
        // TODO make this work when using db queries
        // Also add a newline to the end of "Times per week"
        /*boolean[] onDay = new boolean[7];
        for (int i=0; i<days.size(); i++) {
            //ret += (days.elementAt(i) ? dayNames[i] + ", " : "");
            onDay[days.elementAt(i).day] = true;
        }
        for (int i=0; i<=6; i++) {
            ret += (onDay[i] ? dayNames[i] + ", " : "");
        }
        ret = ret.substring(0,ret.length()-2);*/
        return ret;
    }
}
