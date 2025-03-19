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

    protected static void saveOfficeHour(OfficeHour newOfficeHour) throws IOException {
        try{
            FileWriter out = new FileWriter("src/data/office_hour.csv", true);
            out.append(newOfficeHour.toString());
            out.append("\n");
            out.close();
        }
        catch (IOException e) {
            System.out.println("File not found");
        }
    }

    protected static void closeNotification(Stage notificationStage) {
        notificationStage.close();
    }
}
