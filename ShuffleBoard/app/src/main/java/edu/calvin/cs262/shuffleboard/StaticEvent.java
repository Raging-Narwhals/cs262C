package edu.calvin.cs262.shuffleboard;

/**
 * Defines the StaticEvent class
 *   Events that are set in a schedule and must not be affected by shuffling
 */

public class StaticEvent {

    int startTime;  // Using values [0-48] with each integer being .5 hr
    int stopTime;  // Using values [0-48] with each integer being .5 hr
    String name;  // Short phrase describing the event
    int ownerID;  // The id of the owner in the database
    boolean days[];
    String[] dayNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};

    public StaticEvent() {
        startTime = stopTime = ownerID = 0;
        days = new boolean[7];
    }

    public StaticEvent(int start, int stop, String newName, int owner, boolean[] day) {
        startTime = start;
        stopTime = stop;
        name = newName;
        ownerID = owner;
        days = day;
    }

    /*
     * Getters and setters
     */

    // Return the startTime
    public int getStartTime() {
        return startTime;
    }

    // Set startTime if new value is [0,48] and startTime < stopTime
    public int setStartTime(int start) throws Exception {
        if (start >= stopTime && 0 <= start && start <= 48) {
            startTime = start;
            return startTime;
        }
        else {
            throw new EventException("StaticEvent setStartTime(): Invalid start time: " + start);
        }
    }

    // Return the stopTime
    public int getStopTime() {
        return stopTime;
    }

    // Set stopTime if new value is [0-48] and startTime > stopTime
    public int setStopTime(int stop) throws Exception{
        if (stop <= startTime  && 0 <= stop && stop <= 48) {
            stopTime = stop;
            return stopTime;
        }
        else {
            throw new EventException("StaticEvent setStopTime(): Invalid stop time " + stop);
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
    public boolean[] setDays(int[] day) throws Exception{
        if (day.length == 0) {
            throw new EventException("StaticEvent setDays(): Cannot set using empty day vector");
        }
        else if (day.length == days.length) {
            for (int i = 0; i < day.length; i++) {
                days[i] = (0 <= day[i] && day[i] <= 6);
                return days;
            }
        }
        throw new EventException("StaticEvent setDays(): day vector incorrect length " + day.length);
    }

    // Set whether an event is scheduled for a specific day
    public boolean setDay(int day, boolean setTo) throws Exception{
        if ( 0 <= day && day <= 6) {
            days[day] = setTo;
            return days[day];
        }
        throw new EventException("StaticEvent setDay(): Invalid day " + day);
    }

    public String toString() {
        String ret = name + "\n";
        ret += (startTime>24) ? toTime(startTime-24) + "PM" : toTime(startTime) + "AM";
        ret += " - ";
        ret += (stopTime>23) ? toTime(stopTime-24) + "PM\n" : toTime(stopTime) + "AM\n";
        for (int i=0; i<=6; i++) {
            ret += (days[i] ? dayNames[i] + ", " : "");
        }
        ret = ret.substring(0,ret.length()-2);
        return ret;
    }

    public String toTime(int time) {
        String ret = "";
        boolean halfHour = (time%2==1);
        ret += halfHour ? Integer.toString((time-1)/2) + ":30" : Integer.toString(time/2) + ":00";
        if (ret.charAt(0)=='0') {
            ret = "12" + ret.substring(1, ret.length());
        }
        return ret;
    }

}
