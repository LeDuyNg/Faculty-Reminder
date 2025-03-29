package s25.cs151.application;

import java.util.Objects;

/**
 * Represents a course with a code, name, and section number.
 */
public class Course implements Comparable<Course>
{
    private final String courseCode;
    private final String courseName;
    private final String sectionNumber;

    /**
     * Constructs Course object with given course code, name, and section number
     *
     * @param courseCode     course code
     * @param courseName     name of the course
     * @param sectionNumber  section number
     */
    public Course(String courseCode, String courseName, String sectionNumber)
    {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.sectionNumber = sectionNumber;
    }

    public String getCourseCode()
    {
        return courseCode;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public String getSectionNumber()
    {
        return sectionNumber;
    }

    /**
     * Compares this course to another course for sorting
     * Sorts in descending order based on course code
     *
     * @param other The other Course to compare to
     * @return A negative number, zero, or a positive number as this course
     *         code is greater than, equal to, or less than other course code
     */
    public int compareTo(Course other)
    {
        return other.courseCode.compareToIgnoreCase(this.courseCode);
    }

    /**
     * Determines whether this course is equal to another object
     * Two courses are considered equal if their course code, name, and section number match (case insensitive)
     *
     * @param obj The object to compare with
     * @return true if the courses are equal, or else false
     */
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return courseCode.equalsIgnoreCase(other.courseCode)
                && courseName.equalsIgnoreCase(other.courseName)
                && sectionNumber.equalsIgnoreCase(other.sectionNumber);
    }

    /**
     * Returns hash code for course object
     *
     * @return hash code based on course code, name, and section number
     */
    public int hashCode()
    {
        return Objects.hash(courseCode.toLowerCase(), courseName.toLowerCase(), sectionNumber.toLowerCase());
    }

    /**
     * Returns string representation of the course
     *
     * @return string containing course code, name, and section number
     */
    public String toString()
    {
        return courseCode + ", " + courseName + ", " + sectionNumber;
    }
}