package s25.cs151.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Scene setup
        Scene scene = new Scene(new HomePage(primaryStage), 800, 500);
        primaryStage.setTitle("Home Page");
        primaryStage.setScene(scene);

        // Set the window at a fixed size and don't allow the user to resize the window
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) throws Exception {
//        launch(args);
        Course course = new Course("test", "123", "abc");
        TimeSlot timeSlot = new TimeSlot(12, 0, 14, 34);
        OfficeHourSchedule officeHourSchedule = new OfficeHourSchedule("Duy", "05/21/2001", timeSlot, course, "blank", "blank");
        Controller.saveSchedule(officeHourSchedule);
        System.out.println(CSVHandler.loadOfficeHourScheduleObjects());

    }
}

