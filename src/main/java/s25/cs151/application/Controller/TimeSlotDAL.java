package s25.cs151.application.Controller;

import s25.cs151.application.Model.TimeSlot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimeSlotDAL implements ModelControllerInt<TimeSlot> {
    public TimeSlotDAL() {};

    @Override
    public ArrayList<TimeSlot> loadModelObjects() {
        // Create a list to store TimeSlot objects
        ArrayList<TimeSlot> list = new ArrayList<>();

        // Define the path to the CSV file
        File file = new File("src/data/time_slot.csv");

        // Try-with-resources ensures the Scanner is closed automatically
        try (Scanner scanner = new Scanner(file)) {
            // Read each line from the CSV file
            while (scanner.hasNextLine()) {
                // Split the line into values using comma as the delimiter
                String[] values = scanner.nextLine().split(",");

                // Parse the start and end time in minutes from the CSV line
                int startTimeInMinute = Integer.parseInt(values[0].trim());
                int endTimeInMinute = Integer.parseInt(values[1].trim());

                // Create a TimeSlot object using the parsed values
                list.add(new TimeSlot(startTimeInMinute, endTimeInMinute));
            }
        } catch (FileNotFoundException e) {
            // Handle the case where the CSV file is not found
            System.out.println("CSV file not found");
        } catch (Exception e) {
            // Rethrow any other exceptions as a RuntimeException
            throw new RuntimeException(e);
        }

        // Return the list of loaded time slots
        return list;
    }

    @Override
    public void saveModelObjects(TimeSlot newTimeSlot) {
        try{
            FileWriter out = new FileWriter("src/data/time_slot.csv", true);
            // Append the TimeSlot data to the file
            out.append(newTimeSlot.toString());
            out.append("\n");
            out.close();
        }
        catch (IOException e) {
            // Print an error message if the file cannot be accessed
            System.out.println("File not found");
        }
    }
}
