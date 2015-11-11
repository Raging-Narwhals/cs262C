package edu.calvin.cs262.shuffleboard;

import java.util.Vector;

/**
 * Defines the DynamicEvent class
 *   Events that are not set and can be shuffled
 *
 * Created by JStay on 10/18/2015.
 */
public class DynamicEvent {

    int timesPerWeek;  // How many times this event should happen this week
    int length;  // How long the event is
    String name;  // Short phrase describing the events
    int ownerID;  // The id of the owner in the database
    Vector<Triple> days;

    class Triple {
        int day;  // Day the event is on (0-6)
        int start;  // When the event is allowed to start
        int stop;  // When the event must be finished by

        public Triple() {
            day = start = stop = 0;
        }

        public Triple(int d, int startTime, int stopTime) {
            day = d;
            start = startTime;
            stop = stopTime;
        }

        /**
         *  Returns true if this time slot already exists or if this
         *  time slot is contained within a preexisting one
         *  Returns false otherwise
         */
        public boolean within(Triple other) {
            if (day == other.day) {
                if (start >= other.start) {
                    if (stop <= other.stop) {
                        return true;
                    }
                }
            }
            return false;
        }

        // Return the day (0=Sunday, 6=Saturday)
        public int getDay() {
            return day;
        }

        // Set the day to the newDay if newDay is [0,6]
        public int setDay(int newDay) throws Exception {
            if (newDay<0 || 6<newDay) {
                throw new EventException("Triple setDay(): Invalid day " + newDay);
            }
            day = newDay;
            return day;
        }

        // Return the start
        public int getStart() {
            return start;
        }

        // Set the start if new value is [0,48] and newStart < stop
        public int setStart(int newStart) throws Exception {
            if (newStart<0 || newStart>48) {
                throw new EventException("Triple setStart(): Invalid start " + newStart);
            } else if (newStart >= stop) {
                throw new EventException("Triple setStart(): start must be before stop");
            }
            start = newStart;
            return start;
        }

        // Return the stop
        public int getStop() {
            return stop;
        }

        // Set the stop if new value is [0,48] and newStop > start
        public int setStop(int newStop) throws Exception {
            if (newStop<0 || newStop>48) {
                throw new EventException("Triple setStop(): Invalid stop " + newStop);
            } else if (newStop <= start) {
                throw new EventException("Triple setStop(): start must be before stop");
            }
            stop = newStop;
            return stop;
        }

    }

    public DynamicEvent() {
        timesPerWeek = length = ownerID = 0;
        days = new Vector<Triple>(0);
    }

    public DynamicEvent(int numTimes, int duration, String newName, int owner, Vector<Triple> day) {
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

}
