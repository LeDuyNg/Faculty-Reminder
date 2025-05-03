package s25.cs151.application.Controller;

import s25.cs151.application.Model.OfficeHour;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OfficeHourDAL implements ModelControllerInt<OfficeHour> {
    public OfficeHourDAL() {};

    @Override
    public ArrayList<OfficeHour> load() {
        ArrayList<OfficeHour> list = new ArrayList<>();

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

    @Override
    public boolean save(OfficeHour newOfficeHour) throws IOException {
        try{
            FileWriter out = new FileWriter("src/data/office_hour.csv", true);
            // Append the OfficeHour data to the file
            out.append(newOfficeHour.toString());
            out.append("\n");
            out.close();
            return true;
        }
        catch (IOException e) {
            // Print an error message if the file cannot be accessed
            System.out.println("File not found");
            return false;
        }
    }
    /**
     * Verifies if the given year is a valid four-digit integer.
     * @param year The year as a string.
     * @return True if the year is between 1000 and 9999, otherwise false.
     */
    public boolean verifyYear(String year) {
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
    public boolean verifyChoseDay(boolean[] days) {
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
    public boolean checkForDuplicate(String semester, int year) {
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
}
