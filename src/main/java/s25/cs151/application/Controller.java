package s25.cs151.application;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;


/**
 * This class is responsible for handling actions when the user clicks on a button
 */
public class Controller extends ActionEvent {
    /**
     * This method moves the user to the Create Office Hour page
     * @param currentStage - the current stage (window) that needs to be closed and replaced
     */
    protected static void createOfficeHour(Stage currentStage) {
        // Close the current stage
        currentStage.close();

        // Set the scene for the Create Office Hour Page
        Scene scene = new Scene(new CreateOfficeHourPage(currentStage), 800, 500);
        currentStage.setTitle("Create Office Hour");
        currentStage.setScene(scene);

        // Set the window at a fixed size and don't allow the user to resize the window
        currentStage.setResizable(false);
        currentStage.show();
    }


    protected static void viewOfficeHours(Stage currentStage)
    {
        currentStage.close();
        Scene scene = new Scene(new ViewOfficeHoursPage(currentStage), 800, 500);
        currentStage.setTitle("View Office Hours");
        currentStage.setScene(scene);

        // Set the window at a fixed size and don't allow the user to resize the window
        currentStage.setResizable(false);
        currentStage.show();

    }

    protected static void viewTimeSlots(Stage currentStage)
    {
        currentStage.close();
        Scene scene = new Scene(new ViewTimeSlotsPage(currentStage), 800, 500);
        currentStage.setTitle("View Time Slots");
        currentStage.setScene(scene);

        // Set the window at a fixed size and don't allow the user to resize the window
        currentStage.setResizable(false);
        currentStage.show();

    }

    /**
     * This method returns the user to the Home Page of the application
     * @param currentStage - the current stage (window) that needs to be closed and replaced
     */
    protected static void returnHomePage(Stage currentStage) {
        // Close the current stage
        currentStage.close();

        // Set the scene for the Home Page
        Scene scene = new Scene(new HomePage(currentStage), 800, 500);
        currentStage.setTitle("Home Page");
        currentStage.setScene(scene);

        // Set the window at a fixed size and don't allow the user to resize the window
        currentStage.setResizable(false);
        currentStage.show();
    }

    /**
     * Saves an OfficeHour object to a CSV file.
     *
     * @param newOfficeHour The OfficeHour object to be saved.
     * @throws IOException If an error occurs while writing to the file.
     */
    protected static void saveOfficeHour(OfficeHour newOfficeHour) throws IOException {
        try{
            FileWriter out = new FileWriter("src/data/office_hour.csv", true);
            // Append the OfficeHour data to the file
            out.append(newOfficeHour.toString());
            out.append("\n");
            out.close();
        }
        catch (IOException e) {
            // Print an error message if the file cannot be accessed
            System.out.println("File not found");
        }
    }

    /**
     * Closes a notification popup.
     *
     * @param notificationStage The stage (window) of the notification to be closed.
     */
    protected static void closeNotification(Stage notificationStage) {
        notificationStage.close();
    }

    protected static void openTimeSlotPage(Stage currentStage) {
        currentStage.close();
        Scene scene = new Scene(new TimeSlotWindow(currentStage), 800, 500);
        currentStage.setTitle("Select Time Slot");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }

    protected static void saveTimeSlot(TimeSlot newTimeSlot) throws IOException {
        try{
            FileWriter out = new FileWriter("src/data/time_slot.csv", true);
            // Append the OfficeHour data to the file
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
