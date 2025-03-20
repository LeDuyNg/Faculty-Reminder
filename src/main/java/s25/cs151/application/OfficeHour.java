package s25.cs151.application;

import java.util.ArrayList;
import java.util.Objects;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OfficeHour implements Comparable<OfficeHour>{
    private final String semester;
    private final int year;
    private final boolean[] days = new boolean[5];

    public OfficeHour() {
        this("None", 0, new boolean[5]);
    }

    public OfficeHour(String semester, int year) {
        this(semester, year, new boolean[5]);
    }

    public OfficeHour(String semester, int year, boolean[] days){
        this.semester = semester;
        this.year = year;
        System.arraycopy(days, 0, this.days, 0, days.length);
    }


    public boolean[] getDays()
    {
        return days;
    }

    public String getSemester()
    {
        return semester;
    }

    public int getYear()
    {
        return year;
    }

    protected ArrayList<String> export() {
        ArrayList<String> value = new ArrayList<>();
        value.add(semester);
        value.add(String.valueOf(year));
        for (boolean day : days) {
            value.add(String.valueOf(day));
        }
        return value;
    }

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
            return this.semester.equals(officeHour.semester) && this.year == officeHour.year;
        }
    }

    @Override
    public int compareTo(OfficeHour o) {
        int result = Integer.compare(o.getYear(), this.getYear());
        if (result == 0) {
            return this.getSemester().compareTo(o.getSemester());
        }
        return result;
    }


    @Override
    public int hashCode() {
        return Objects.hash(semester, year);
    }

    @Override
    public String toString() {
        return export().toString().substring(1, export().toString().length() - 1);
    }
}
