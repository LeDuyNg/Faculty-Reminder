package s25.cs151.application.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OfficeHourSchedule implements Comparable<OfficeHourSchedule>{
    private String studentName;
    private String scheduleDate;
    private TimeSlot timeSlot;
    private Course course;
    private String reason;
    private String comment;

    /**
     * Constructs a new OfficeHourSchedule with the provided details.
     *
     * @param studentName  the name of the student
     * @param scheduleDate the date of the scheduled office hour
     * @param timeSlot     the time slot of the appointment
     * @param course       the course associated with the office hour
     * @param reason       the reason for scheduling the office hour
     * @param comment      any additional comments from the student
     */
    public OfficeHourSchedule(String studentName, String scheduleDate, TimeSlot timeSlot, Course course, String reason, String comment) {
        this.studentName = studentName;
        this.scheduleDate = scheduleDate;
        this.timeSlot = timeSlot;
        this.course = course;
        this.reason = reason;
        this.comment = comment;
    }

    /**
     * @return the name of the student
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @return the scheduled date of the office hour
     */
    public String getScheduleDate() {
        return scheduleDate;
    }

    /**
     * @return the time slot of the appointment
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * @return the course associated with the office hour
     */
    public Course getCourse() {
        return course;
    }

    /**
     * @return the reason for the office hour appointment
     */
    public String getReason() {
        return reason;
    }

    /**
     * @return any additional comment provided by the student
     */
    public String getComment() {
        return comment;
    }

    public void setStudentName(String studentName)
    {
        this.studentName = studentName;
    }

    public void setCourse(Course course)
    {
        this.course = course;
    }

    public void setScheduleDate(String scheduleDate)
    {
        this.scheduleDate = scheduleDate;
    }

    public void setTimeSlot(TimeSlot timeSlot)
    {
        this.timeSlot = timeSlot;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }


    /**
     * Checks equality between this OfficeHourSchedule and another object.
     * Two schedules are considered equal if all their fields match, ignoring case for reason and comment.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof OfficeHourSchedule)) {
            return false;
        }
        else {
            OfficeHourSchedule officeHourSchedule = (OfficeHourSchedule) obj;
            return this.studentName.equals(officeHourSchedule.studentName)
                    && this.scheduleDate.equals(officeHourSchedule.scheduleDate)
                    && this.timeSlot.equals(officeHourSchedule.timeSlot)
                    && this.course.equals(officeHourSchedule.course)
                    && this.reason.equalsIgnoreCase(officeHourSchedule.reason)
                    && this.comment.equalsIgnoreCase(officeHourSchedule.comment);
        }
    }

    /**
     * Compares this OfficeHourSchedule to another for sorting purposes.
     * Sorting is done first by ascending scheduleDate, then by timeSlot.
     *
     * @param o the other OfficeHourSchedule to compare with
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to,
     *         or greater than the specified object
     */
    @Override
    public int compareTo(OfficeHourSchedule o) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate thisDate = LocalDate.parse(this.scheduleDate, formatter);
        LocalDate otherDate = LocalDate.parse(o.scheduleDate, formatter);
        int result = thisDate.compareTo(otherDate);
        if (result == 0) {
            return this.timeSlot.compareTo(o.timeSlot);
        }
        return result;
    }

    /**
     * Collects all field values into a list, useful for export or display.
     *
     * @return an ArrayList containing all field values
     */
    private ArrayList<Object> export() {
        ArrayList<Object> value = new ArrayList<>();
        value.add(studentName);
        value.add(scheduleDate);
        value.add(timeSlot);
        value.add(course);
        value.add(reason);
        value.add(comment);
        return value;
    }

    /**
     * Returns a readable string representation of the OfficeHourSchedule.
     * The result is a comma-separated list of field values.
     *
     * @return a string containing all field values
     */
    @Override
    public String toString() {
        return String.join(", ",
                safe(getStudentName()),
                safe(getScheduleDate()),
                String.valueOf(getTimeSlot().getStartTimeInMinutes()),
                String.valueOf(getTimeSlot().getEndTimeInMinutes()),
                safe(getCourse().getCourseCode()),
                safe(getCourse().getCourseName()),
                safe(getCourse().getSectionNumber()),
                safe(getReason()),
                safe(getComment())
        );
    }

    /**
     * Ensures null values are safely converted to empty strings
     */
    private String safe(String value) {
        return value == null ? "" : value;
    }
}