package s25.cs151.application;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Thí class represents an OfficeHour
 */
public class OfficeHour implements Comparable<OfficeHour>{
    private final String semester;
    private final int year;
    private final boolean[] days = new boolean[5];
    private final int semesterValue;

    /**
     * Default constructor
     */
    public OfficeHour() {
        this("Spring", 0, new boolean[5]);
    }

    /**
     * Constructor that initializes OfficeHour with a specific semester and year.
     * @param semester The semester name
     * @param year The academic year.
     */
    public OfficeHour(String semester, int year) {
        this(semester, year, new boolean[5]);
    }

    /**
     * Constructor that initializes OfficeHour with a specific semester, year, and office hour days.
     * @param semester The semester name.
     * @param year The academic year.
     * @param days A boolean array representing office hour availability for each weekday.
     */
    public OfficeHour(String semester, int year, boolean[] days){
        this.semester = semester;
        this.year = year;
        System.arraycopy(days, 0, this.days, 0, days.length);
        if (semester.equals("Spring")) {
            semesterValue = 0;
        }
        else if (semester.equals("Summer")) {
            semesterValue = 1;
        }
        else if (semester.equals("Fall")) {
            semesterValue = 2;
        }
        else {
            semesterValue = 3;
        }
    }

    /**
     * Gets the office hour availability days.
     * @return A boolean array representing office hour days (Monday - Friday).
     */
    public boolean[] getDays()
    {
        return days;
    }

    /**
     * Gets the semester name.
     * @return The semester as a string.
     */
    public String getSemester()
    {
        return semester;
    }

    /**
     * Gets the academic year.
     * @return The year as an integer.
     */
    public int getYear()
    {
        return year;
    }

    /**
     * Exports the OfficeHour details as an ArrayList of Strings.
     * @return An ArrayList containing semester, year, and office hour availability.
     */
    protected ArrayList<String> export() {
        ArrayList<String> value = new ArrayList<>();
        value.add(semester);
        value.add(String.valueOf(year));
        for (boolean day : days) {
            value.add(String.valueOf(day));// Convert boolean values to strings
        }
        return value;
    }

    /**
     * Compares this OfficeHour instance with another based on semester and year.
     * @param obj The object to compare with.
     * @return True if the semester and year match, otherwise false.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof OfficeHour)) {
            return false;
        }
        else {
            OfficeHour officeHour = (OfficeHour) obj;
            return this.semesterValue == officeHour.semesterValue && this.year == officeHour.year;
        }
    }

    /**
     * Compares OfficeHour objects first by year (descending), then by semester (order of the season).
     * @param o The OfficeHour object to compare with.
     * @return A negative, zero, or positive value based on the comparison.
     */
    @Override
    public int compareTo(OfficeHour o) {
        int result = Integer.compare(o.getYear(), this.getYear());
        if (result == 0) {
            return Integer.compare(o.semesterValue, this.semesterValue);
        }
        return result;
    }

    /**
     * Generates a hash code based on the semester and year.
     * @return The computed hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(semester, year);
    }

    /**
     * Converts the OfficeHour object to a formatted string representation.
     * @return A string containing the semester, year, and office hour availability.
     */
    @Override
    public String toString() {
        return export().toString().substring(1, export().toString().length() - 1);
    }
}
