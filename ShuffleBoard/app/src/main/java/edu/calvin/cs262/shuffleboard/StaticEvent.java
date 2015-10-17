package edu.calvin.cs262.shuffleboard;

/**
 * Defines the StaticEvent class
 *   Events that are set in a schedule and must not be affected by shuffling
 *
 * Created by JStay on 10/16/2015.
 */

public class StaticEvent {

    int startTime;  // Using values [0-48] with each integer being .5 hr
    int stopTime;  // Using values [0-48] with each integer being .5 hr
    int ownerID;  // The id of the owner in the database
    boolean days[];

    public StaticEvent() {
        startTime = stopTime = ownerID = 0;
        days = new boolean[7];
    }

    public StaticEvent(int start, int stop, int owner, boolean[] day) {
        startTime = start;
        stopTime = stop;
        ownerID = owner;
        days = day;
    }

    /*
     * Getters and setters
     * TODO change "return -1" to throwing exceptions
     */

    // Return the startTime
    public int getStartTime() {
        return startTime;
    }

    // Set startTime if new value is [0,48] and startTime < stopTime
    public int setStartTime(int start) {
        if (start >= stopTime && 0 <= start && start <= 48) {
            startTime = start;
            return startTime;
        }
        else {
            return -1;  // TODO make this an exception
        }
    }

    // Return the stopTime
    public int getStopTime() {
        return stopTime;
    }

    // Set stopTime if new value is [0-48] and startTime > stopTime
    public int setStopTime(int stop) {
        if (stop <= startTime  && 0 <= stop && stop <= 48) {
            stopTime = stop;
            return stopTime;
        }
        else {
            return -1;  // TODO make this an exception
        }
    }

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
    public boolean[] getDays() {
        return days;
    }

    // Set the days that the event is on
    public boolean[] setDays(int[] day) {
        if (day.length == 0) {
            // TODO change this to throwing exception
            return days;
        }
        else if (day.length == days.length) {
            for (int i = 0; i < day.length; i++) {
                days[i] = (0 <= day[i] && day[i] <= 6);
                return days;
            }
        }
        // TODO Change this to throwing exception
        return days;
    }

    // Set whether an event is scheduled for a specific day
    public boolean setDay(int day, boolean setTo) {
        if ( 0 <= day && day <= 6) {
            days[day] = setTo;
        }
        return days[day];
    }

}
