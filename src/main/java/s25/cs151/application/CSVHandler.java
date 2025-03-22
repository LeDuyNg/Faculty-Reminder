package s25.cs151.application;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class is for handling all of the actions related to CSV file and OfficeHour objects
 */
public class CSVHandler {
    /**
     * Verifies if the given year is a valid four-digit integer.
     * @param year The year as a string.
     * @return True if the year is between 1000 and 9999, otherwise false.
     */
    protected static boolean verifyYear(String year) {
        try{
            int yearInt = Integer.parseInt(year);
            return yearInt <= 9999 && yearInt >= 1000;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if at least one day is selected.
     * @param days An array representing selected days (true if selected).
     * @return True if at least one day is selected, otherwise false.
     */
    protected static boolean verifyChoseDay(boolean[] days) {
        for (boolean day : days) {
            if (day) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an office hour entry already exists in the CSV file.
     * @param semester The semester name (e.g., Spring, Fall).
     * @param year The year of the office hour.
     * @return True if a duplicate entry is found, otherwise false.
     */
    protected static boolean checkForDuplicate(String semester, int year) {
        File importFile = new File("src/data/office_hour.csv");
        try {
            ArrayList<OfficeHour> officeHourList = new ArrayList<>();
            Scanner input = new Scanner(importFile);

            // Read each line from the CSV file and add to the list
            while (input.hasNextLine()) {
                // The data from the .csv file are seperated by ", "
                String[] values = input.nextLine().split(",");// Strip the "," from the data
                // Strip the space before the data
                // values[0] = semester
                // values[1] = year
                OfficeHour temp = new OfficeHour(values[0].trim(), Integer.parseInt(values[1].trim()));
                officeHourList.add(temp);
            }
            input.close();

            // Create a new OfficeHour object and check if it exists in the list
            OfficeHour current = new OfficeHour(semester, year);
            return officeHourList.contains(current);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Formats OfficeHour object into string including semester, year, and selected days
     *
     * @param officeHour OfficeHour object containing semester, year, and day selections
     * @return formatted string
     */
    private static String formatOfficeHour(OfficeHour officeHour)
    {
        String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        StringBuilder formattedDays = new StringBuilder();

        boolean[] days = officeHour.getDays();
        for (int i = 0; i < days.length; i++) {
            if (days[i]) {
                if (formattedDays.length() > 0) {
                    formattedDays.append(", ");
                }
                formattedDays.append(daysOfWeek[i]);
            }
        }

        // Return formatted string with semester, year, and days
        return officeHour.getSemester() + " " + officeHour.getYear() + " - Days: " + formattedDays.toString();
    }

    /**
     * Loads office hour data from CSV file and returns it as formatted strings
     * Data is sorted in descending order by year and semester
     *
     * @return ObservableList of formatted office hour strings to be displayed
     */
    protected static ObservableList<String> loadOfficeHoursFromCSV()
    {
        ObservableList<String> officeHours = FXCollections.observableArrayList();
        File file = new File("src/data/office_hour.csv");
        List<OfficeHour> officeHourList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");

                if (values.length < 7) {
                    continue;
                }

                String semester = values[0].trim();
                int year = Integer.parseInt(values[1].trim());
                boolean[] days = new boolean[5];

                for (int i = 0; i < 5; i++) {
                    days[i] = Boolean.parseBoolean(values[i + 2].trim());
                }

                officeHourList.add(new OfficeHour(semester, year, days));
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found!");
        }

        // Sort list of office hours by descending year then semester
        Collections.sort(officeHourList);

        for (OfficeHour officeHour : officeHourList) {
            officeHours.add(formatOfficeHour(officeHour));
        }

        return officeHours;
    }

    /**
     * Converts boolean representing weekdays into readable string
     * Each element in the array corresponds to a weekday (Monday to Friday)
     * where 'true' means that day is selected
     *
     * @param days a boolean array of size 5 representing Monday through Friday
     * @return a comma-separated string of the selected day names, or "None" if none are selected
     */
    private static String formatDays(boolean[] days) {
        String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < days.length; i++) {
            if (days[i]) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(dayNames[i]);
            }
        }
        return sb.toString().isEmpty() ? "None" : sb.toString();
    }

    /**
     * Loads OfficeHour objects from CSV file
     * File is expected to be located at "src/data/office_hour.csv" and
     * each line must be formatted as: Semester, Year, Weekdays]
     *
     * @return a list of OfficeHour objects parsed from CSV file
     */
    public static List<OfficeHour> loadOfficeHourObjects()
    {
        List<OfficeHour> list = new ArrayList<>();
        File file = new File("src/data/office_hour.csv");

        try (Scanner scanner = new Scanner(file))
        {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(",");
                String semester = values[0].trim();
                int year = Integer.parseInt(values[1].trim());
                boolean[] days = new boolean[5];
                for (int i = 0; i < 5; i++) {
                    days[i] = Boolean.parseBoolean(values[i + 2].trim());
                }
                list.add(new OfficeHour(semester, year, days));
            }
        } catch (FileNotFoundException e) {
            System.out.println("CSV file not found");
        }
        return list;
    }

    /**
     * Displays a notification with the given message in a new pop-up window.
     * @param message The message to be displayed.
     */
    protected static void displayNotification(String message) {
        Stage newStage = new Stage();
        Scene newScene = new Scene(new Notifier(message, newStage));
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.show();
    }
}
