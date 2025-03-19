package s25.cs151.application;

import java.util.ArrayList;
import java.util.Objects;

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
    public int compareTo(OfficeHour officeHour) {
        int result = this.semester.compareTo(officeHour.semester);
        if (result == 0) {
            result = Integer.compare(this.year, officeHour.year);
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
