package s25.cs151.application.Controller;

import s25.cs151.application.Model.Course;
import s25.cs151.application.Model.OfficeHourSchedule;
import s25.cs151.application.Model.TimeSlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OfficeHourScheduleDAO implements DAOInt<OfficeHourSchedule> {
    public OfficeHourScheduleDAO(){};


    @Override
    public ArrayList<OfficeHourSchedule> load() {
        ArrayList<OfficeHourSchedule> list = new ArrayList<>();

        // Define the path to the CSV file
        File file = new File("src/data/office_hour_schedule.csv");

        // Try-with-resources ensures the Scanner is closed automatically
        try (Scanner scanner = new Scanner(file)) {
            // Read each line from the CSV file
            while (scanner.hasNextLine()) {
                // Split the line into values using comma as the delimiter
                String[] values = scanner.nextLine().split(",");

                // Parse and trim each value from the CSV line
                String studentName = values[0].trim();
                String scheduleDate = values[1].trim();
                int startTimeInMinute = Integer.parseInt(values[2].trim());
                int endTimeInMinute = Integer.parseInt(values[3].trim());
                String courseCode = values[4].trim();
                String courseName = values[5].trim();
                String sectionNumber = values[6].trim();
                String reason = values[7].trim();
                String comment = values.length > 8 ? values[8].trim() : "";

                // Create TimeSlot and Course objects
                TimeSlot timeSlot = new TimeSlot(startTimeInMinute, endTimeInMinute);
                Course course = new Course(courseCode, courseName, sectionNumber);

                // Create and add the OfficeHourSchedule to the list
                OfficeHourSchedule officeHourSchedule = new OfficeHourSchedule(
                        studentName, scheduleDate, timeSlot, course, reason, comment
                );
                list.add(officeHourSchedule);
            }
        } catch (FileNotFoundException e) {
            // Handle the case where the CSV file is not found
            System.out.println("CSV file not found");
        } catch (Exception e) {
            // Rethrow any other unexpected exceptions
            throw new RuntimeException(e);
        }

        // Return the list of loaded schedules
        return list;
    }


    @Override
    public boolean save(OfficeHourSchedule newSchedule) throws IOException {
        try {
            FileWriter out = new FileWriter("src/data/office_hour_schedule.csv", true);
            out.append(newSchedule.toString());
            out.append("\n");
            out.close();
            return true;
        } catch (IOException e) {
            // Print an error message if the file cannot be accessed
            System.out.println("File not found");
            return false;
        }
    }

    /**
     * Removes entry from the office_hour_schedule.csv file
     * @param target object to be removed
     * @return true if schedule was found and removed, false otherwise
     */
    public boolean removeOfficeHourSchedule(OfficeHourSchedule target) {
        File file = new File("src/data/office_hour_schedule.csv");
        List<OfficeHourSchedule> schedules = load();

        boolean removed = schedules.remove(target);
        if (removed) {
            try (FileWriter writer = new FileWriter(file, false)) {
                for (OfficeHourSchedule s : schedules) {
                    writer.write(s.toString() + "\n");
                }
            } catch (IOException e) {
                System.out.println("Failed to rewrite schedule CSV: " + e.getMessage());
            }
        }

        return removed;
    }

    /**
     * Rewrites the entire office hour schedule CSV with the provided list of schedules.
     * @param schedules the list of OfficeHourSchedule objects to write to file
     */
    public void rewriteAllSchedules(List<OfficeHourSchedule> updatedSchedules) {
        File file = new File("src/data/office_hour_schedule.csv");

        try (FileWriter writer = new FileWriter(file, false)) {
            for (OfficeHourSchedule schedule : updatedSchedules) {
                writer.write(schedule.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Failed to rewrite schedule CSV: " + e.getMessage());
        }
    }
}
