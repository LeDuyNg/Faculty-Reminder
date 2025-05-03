package s25.cs151.application.Controller;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import s25.cs151.application.View.*;




/**
 * This class is responsible for handling actions when the user clicks on a button
 */
public class Controller extends ActionEvent {
    /**
     * This method moves the user to the Create Office Hour page
     * @param currentStage - the current stage (window) that needs to be closed and replaced
     */
    public static void createOfficeHour(Stage currentStage) {
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


    /**
     * Opens the "View Office Hours" page by replacing the current scene with a new one.
     * The window is set to a fixed size and is not resizable.
     *
     * @param currentStage The current JavaFX stage to be updated.
     */
    public static void viewOfficeHours(Stage currentStage)
    {
        // Close the current window
        currentStage.close();

        // Create a new scene with the ViewOfficeHoursPage layout
        Scene scene = new Scene(new ViewOfficeHoursPage(currentStage), 800, 500);

        // Set the title of the window
        currentStage.setTitle("View Office Hours");

        // Set the new scene to the stage
        currentStage.setScene(scene);

        // Prevent window resizing
        currentStage.setResizable(false);

        // Show the new window
        currentStage.show();
    }

    /**
     * Opens the "View Time Slots" page by replacing the current scene with a new one.
     * The window is set to a fixed size and is not resizable.
     *
     * @param currentStage The current JavaFX stage to be updated.
     */
    public static void viewTimeSlots(Stage currentStage)
    {
        // Close the current window
        currentStage.close();

        // Create a new scene with the ViewTimeSlotsPage layout
        Scene scene = new Scene(new ViewTimeSlotsPage(currentStage), 800, 500);

        // Set the title of the window
        currentStage.setTitle("View Time Slots");

        // Set the new scene to the stage
        currentStage.setScene(scene);

        // Prevent window resizing
        currentStage.setResizable(false);

        // Show the new window
        currentStage.show();
    }


    /**
     * This method returns the user to the Home Page of the application
     * @param currentStage - the current stage (window) that needs to be closed and replaced
     */
    public static void returnHomePage(Stage currentStage) {
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
    public static void openTimeSlotPage(Stage currentStage) {
        currentStage.close();
        Scene scene = new Scene(new CreateTimeSlotPage(currentStage), 400, 300);
        currentStage.setTitle("Select Time Slot");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }

    public static void openCoursePage(Stage currentStage) {
        currentStage.close();
        Scene scene = new Scene(new CreateCoursePage(currentStage), 400, 300);
        currentStage.setTitle("Enter Course Details");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }

    public static void openCreateSchedulePage(Stage currentStage) {
        currentStage.close();
        Scene scene = new Scene(new CreateSchedulePage(currentStage), 800, 500);
        currentStage.setTitle("Create Schedule");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }

    public static void openViewSchedulePage(Stage currentStage) {
        currentStage.close();
        Scene scene = new Scene(new ViewSchedulePage(currentStage), 1400, 800);
        currentStage.setTitle("View Schedule");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }


    public static void openViewCoursesPage(Stage currentStage) {
        currentStage.close();
        Scene scene = new Scene(new ViewCoursesPage(currentStage), 800, 500);
        currentStage.setTitle("View Courses");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }

    /**
     * Opens Edit Schedule page
     * @param currentStage current stage to be updated
     */
    public static void openEditSchedulePage(Stage currentStage)
    {
        currentStage.close();
        Scene scene = new Scene(new EditSchedulePage(currentStage), 1400, 800);
        currentStage.setTitle("Edit Schedule");
        currentStage.setScene(scene);
        currentStage.setResizable(false);
        currentStage.show();
    }

    /**
     * Closes notification popup.
     *
     * @param notificationStage The stage (window) of the notification to be closed.
     */
    public static void closeNotification(Stage notificationStage) {
        notificationStage.close();
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
