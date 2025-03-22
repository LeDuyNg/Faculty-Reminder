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

public class CSVHandler {
    protected static boolean verifyYear(String year) {
        try{
            int yearInt = Integer.parseInt(year);
            return yearInt <= 9999 && yearInt >= 1000;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    protected static boolean verifyChoseDay(boolean[] days) {
        for (boolean day : days) {
            if (day) {
                return true;
            }
        }
        return false;
    }

    protected static boolean checkForDuplicate(String semester, int year) {
        File importFile = new File("src/data/office_hour.csv");
        try {
            ArrayList<OfficeHour> officeHourList = new ArrayList<>();
            Scanner input = new Scanner(importFile);
            while (input.hasNextLine()) {
                String[] values = input.nextLine().split(",");
                OfficeHour temp = new OfficeHour(values[0].trim(), Integer.parseInt(values[1].trim()));
                officeHourList.add(temp);
            }
            OfficeHour current = new OfficeHour(semester, year);
            return officeHourList.contains(current);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

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

        return officeHour.getSemester() + " " + officeHour.getYear() + " - Days: " + formattedDays.toString();
    }

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

    protected static void displayNotification(String message)
    {
        Stage newStage = new Stage();
        Scene newScene = new Scene(new Notifier(message, newStage));
        newStage.setScene(newScene);
        newStage.show();
    }
}
