package s25.cs151.application;

import java.util.ArrayList;

public class TimeSlot implements Comparable<TimeSlot> {
    private final int startTimeInMinutes;
    private final int endTimeInMinutes;
    private final int duration;

    public TimeSlot() throws Exception {
        this(0, 0, 0, 0);
    }

    public TimeSlot(int startTimeInMinutes, int endTimeInMinutes) throws Exception{
        this.startTimeInMinutes = startTimeInMinutes;
        this.endTimeInMinutes = endTimeInMinutes;
        duration = endTimeInMinutes - startTimeInMinutes;
        if (duration < 0) {
            throw new Exception("End time must be after start time.");
        }
    }

    public TimeSlot(int startHour, int startMinute, int endHour, int endMinute) throws Exception {
        startTimeInMinutes = startHour * 60 + startMinute;
        endTimeInMinutes = endHour * 60 + endMinute;
        duration = endTimeInMinutes - startTimeInMinutes;
        if (duration < 0) {
            throw new Exception("End time must be after start time.");
        }
    }

    public int getStartTimeInMinutes() {return startTimeInMinutes;}

    public int getEndTimeInMinutes() {return endTimeInMinutes;}

    public int getDuration() {return duration;}

    private ArrayList<Integer> export() {
        ArrayList<Integer> value = new ArrayList<>();
        value.add(startTimeInMinutes);
        value.add(endTimeInMinutes);
        return value;
    }

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

    @Override
    public int compareTo(TimeSlot o) {
        int result = Integer.compare(this.startTimeInMinutes, o.startTimeInMinutes);
        if (result == 0) {
            return Integer.compare(this.endTimeInMinutes, o.endTimeInMinutes);
        }
        return result;
    }

    @Override
    public String toString() {
        return export().toString().substring(1, export().toString().length() - 1);
    }

    public String formatTimetoString(int timeInMinutes) {
        int hour = timeInMinutes / 60;
        int minutes = timeInMinutes % 60;
        return String.format("%02d : %02d", hour, minutes);
    }
}
