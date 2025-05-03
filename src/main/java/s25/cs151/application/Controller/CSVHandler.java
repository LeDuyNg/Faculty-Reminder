package s25.cs151.application.Controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import s25.cs151.application.Model.OfficeHour;
import s25.cs151.application.View.Notifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    public static boolean verifyYear(String year) {
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
    public static boolean verifyChoseDay(boolean[] days) {
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
    public static boolean checkForDuplicate(String semester, int year) {
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
     * Displays notification with given message in new pop-up window
     * @param message The message to be displayed
     */
    public static void displayNotification(String message)
    {
        Stage newStage = new Stage();
        Scene newScene = new Scene(new Notifier(message, newStage));
        newStage.setScene(newScene);
        newStage.setResizable(false);
        newStage.show();
    }

}
