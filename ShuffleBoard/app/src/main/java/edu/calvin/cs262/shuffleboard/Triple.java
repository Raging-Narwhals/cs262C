package edu.calvin.cs262.shuffleboard;

/**
 * Defines the Triple class: used to represent a specified period of time on a certain day
 *   (day, start, end)
 */
public class Triple {
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