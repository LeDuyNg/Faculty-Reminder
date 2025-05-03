package s25.cs151.application.Controller;

import s25.cs151.application.Model.Course;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class handles loading, saving and checking for duplicate courses from csv
 */
public class CourseDAO implements DAOInt<Course>
{
    public CourseDAO() {};

    /**
     * Loads courses from CSV file
     *
     * @return list of Course objects loaded from  file
     */
    @Override
    public  ArrayList<Course> load()
    {
        ArrayList<Course> courseList = new ArrayList<>();
        File file = new File("src/data/course.csv");

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                if (values.length < 3) continue;

                String code = values[0].trim();
                String name = values[1].trim();
                String section = values[2].trim();

                courseList.add(new Course(code, name, section));
            }
        } catch (FileNotFoundException e) {
            System.out.println("course.csv file not found!");
        }

        return courseList;
    }

    /**
     * Checks whether course is already in CSV file
     * Duplicate is determined based on course code and section
     *
     * @param course course to check
     * @return true if course is a duplicate, or else false
     */
    public boolean isDuplicate(Course course)
    {
        List<Course> existing = load();
        return existing.contains(course);
    }

    /**
     * Saves course to CSV file if it's not duplicate
     *
     * @param course Course object to save
     * @return true if course was saved successfully, false if it was a duplicate or an error occurred
     */
    @Override
    public boolean save(Course course)
    {
        if (isDuplicate(course)) return false;
        try (java.io.FileWriter writer = new java.io.FileWriter("src/data/course.csv", true))
        {
            writer.write(course.getCourseCode() + "," + course.getCourseName() + "," + course.getSectionNumber() + "\n");
            return true;
        } catch (Exception e) {
            System.out.println("Error saving course: " + e.getMessage());
            return false;
        }
    }
}