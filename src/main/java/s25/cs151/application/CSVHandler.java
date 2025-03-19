package s25.cs151.application;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class CSVHandler {
    protected static boolean verifyYear(String year) {
        try{
            int yearInt = Integer.parseInt(year);
            if (yearInt > 9999 || yearInt < 1000) {
                return false;
            }
            return true;
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

    protected static void displayNotification(String message) {
        Stage newStage = new Stage();
        Scene newScene = new Scene(new Notifier(message, newStage));
        newStage.setScene(newScene);
        newStage.show();
    }
}
