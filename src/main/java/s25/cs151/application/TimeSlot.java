package s25.cs151.application;

import java.util.ArrayList;

/**
 * Represents a time slot with a start time, end time, and duration in minutes.
 * Implements Comparable to allow sorting by start and end times.
 */
public class TimeSlot implements Comparable<TimeSlot> {
    private final int startTimeInMinutes;
    private final int endTimeInMinutes;
    private final int duration;

    /**
     * Default constructor that initializes a TimeSlot with zero duration.
     */
    public TimeSlot() throws Exception {
        this(0, 0, 0, 0);
    }

    /**
     * Constructs a TimeSlot using start and end times in minutes.
     * @param startTimeInMinutes Start time in minutes from midnight.
     * @param endTimeInMinutes End time in minutes from midnight.
     * @throws Exception if the end time is before the start time.
     */
    public TimeSlot(int startTimeInMinutes, int endTimeInMinutes) throws Exception{
        this.startTimeInMinutes = startTimeInMinutes;
        this.endTimeInMinutes = endTimeInMinutes;
        duration = endTimeInMinutes - startTimeInMinutes;
        if (duration < 0) {
            throw new Exception("End time must be after start time.");
        }
    }

    /**
     * Constructs a TimeSlot using start and end hours and minutes.
     * @param startHour Start hour (0–23)
     * @param startMinute Start minute (0–59)
     * @param endHour End hour (0–23)
     * @param endMinute End minute (0–59)
     * @throws Exception if the end time is before the start time.
     */
    public TimeSlot(int startHour, int startMinute, int endHour, int endMinute) throws Exception {
        startTimeInMinutes = startHour * 60 + startMinute;
        endTimeInMinutes = endHour * 60 + endMinute;
        duration = endTimeInMinutes - startTimeInMinutes;
        if (duration < 0) {
            throw new Exception("End time must be after start time.");
        }
    }

    public TimeSlot(String formatedTimeSlot) throws Exception{
        this(Integer.parseInt(formatedTimeSlot.substring(0, 2)), // startHour
                Integer.parseInt(formatedTimeSlot.substring(5, 7)), // startMinute
                Integer.parseInt(formatedTimeSlot.substring(10, 12)), // endHour
                Integer.parseInt(formatedTimeSlot.substring(15))); // endMinute
    }

    // Getters
    public int getStartTimeInMinutes() { return startTimeInMinutes; }

    public int getEndTimeInMinutes() { return endTimeInMinutes; }

    public int getDuration() { return duration; }

    /**
     * Exports the start and end times as a list of integers.
     * @return ArrayList containing [startTimeInMinutes, endTimeInMinutes]
     */
    private ArrayList<Integer> export() {
        ArrayList<Integer> value = new ArrayList<>();
        value.add(startTimeInMinutes);
        value.add(endTimeInMinutes);
        return value;
    }

    /**
     * Checks if this TimeSlot is equal to another object.
     * Two TimeSlots are equal if their start and end times match.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof TimeSlot)) {
            return false;
        }
        else {
            TimeSlot timeSlot = (TimeSlot) obj;
            return this.startTimeInMinutes == timeSlot.startTimeInMinutes
                    && this.endTimeInMinutes == timeSlot.endTimeInMinutes;
        }
    }

    /**
     * Compares two TimeSlot objects for ordering.
     * Primary comparison is based on start time, then end time.
     */
    @Override
    public int compareTo(TimeSlot o) {
        int result = Integer.compare(this.startTimeInMinutes, o.startTimeInMinutes);
        if (result == 0) {
            return Integer.compare(this.endTimeInMinutes, o.endTimeInMinutes);
        }
        return result;
    }

    /**
     * Returns a string representation of the TimeSlot.
     * Format: "startTimeInMinutes, endTimeInMinutes"
     */
    @Override
    public String toString() {
        return export().toString().substring(1, export().toString().length() - 1);
    }

    /**
     * Formats a time (in minutes) into "HH : MM" string format.
     * @param timeInMinutes Time in minutes from midnight.
     * @return Formatted string in "HH : MM"
     */
    public String formatTimetoString(int timeInMinutes) {
        int hour = timeInMinutes / 60;
        int minutes = timeInMinutes % 60;
        return String.format("%02d : %02d", hour, minutes);
    }

    /**
     * Formats the time slot as a string in the format "start time - end time".
     *
     * @return A string representing the time slot in the format "HH:mm - HH:mm", where
     *         the first time is the start time and the second time is the end time.
     */
    public String formatTimeSlottoString() {
        return String.format("%s - %s", formatTimetoString(startTimeInMinutes), formatTimetoString(endTimeInMinutes));
    }


    /**
     * Gets the start time in minutes
     * @return Start time in minutes
     */
    public int getStartTime() {
        return startTimeInMinutes;
    }

    /**
     * Gets start time in minutes
     * @return Start time in minutes
     */
    public int getEndTime() {
        return endTimeInMinutes;
    }

}
