package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 * This class creates the "Home" page
 */
class HomePage extends BorderPane {
    /**
     * Constructor to initialize the Home Page.
     *
     * @param currentStage The current stage (window) that needs to be replaced with the "Home" page.
     */
    HomePage(Stage primaryStage) {
        super();
        // Center Buttons: Creating the buttons for the user to interact with
        CustomizeButton createButton = new CustomizeButton(200, 30,
                "Create Office Hour", "#CAA8F5");

        CustomizeButton editButton = new CustomizeButton(200, 30,
                "Edit Office Hour", "#CAA8F5");

        CustomizeButton searchButton = new CustomizeButton(200, 30,
                "Search Schedule", "#CAA8F5");

        CustomizeButton viewButton = new CustomizeButton(200, 30, "View Office Hours", "#CAA8F5");

        CustomizeButton exitButton = new CustomizeButton(200, 30, "Exit", "#CAA8F5");

        // Creating the banner for the application
        Rectangle appBannerBox = new Rectangle();
        Label appName = new Label("Faculty Reminder");
        appName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Set the dimensions and color for the banner
        appBannerBox.setHeight(50);
        appBannerBox.setWidth(200);
        appBannerBox.setFill(Color.valueOf("#B8B8FF"));

        // Placing the banner box and app's name in the StackPane
        StackPane banner = new StackPane();
        banner.getChildren().addAll(appBannerBox, appName);

        // Creating a container for the buttons
        VBox centerButtons = new VBox(10, createButton, editButton, searchButton, viewButton, exitButton);
        centerButtons.setAlignment(Pos.CENTER);

        // Adding the banner and the buttons to a center layout
        VBox centerLayout = new VBox(20, banner, centerButtons);
        centerLayout.setAlignment(Pos.CENTER);

        // Left Pane: Schedule Box with Course Dropdown and Calendar inside
        VBox scheduleContainer = new VBox(0);
        scheduleContainer.setStyle("-fx-border-color: #8A2BE2; -fx-border-width: 2; -fx-padding: 10;");
        scheduleContainer.setPrefSize(250, 400);
        scheduleContainer.setAlignment(Pos.CENTER_LEFT);

        // Creating the label for "All Schedules"
        Label allScheduleLabel = new Label("All Schedules");
        allScheduleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Creating an All Schedule box
        Rectangle allScheduleBox = new Rectangle(250, 30);
        allScheduleBox.setFill(Color.BLACK);

        // StackPane for "All Schedule" box and "All Schedule" label
        StackPane allSchedulePane = new StackPane();
        allSchedulePane.getChildren().addAll(allScheduleBox,allScheduleLabel);

        // Creating the drop-down list for the courses
        ComboBox<String> courseComboBox = new ComboBox<>();
        courseComboBox.getItems().addAll("CS151", "CS154", "CS174");
        courseComboBox.setPromptText("Select Course");
        courseComboBox.setPrefSize(250,30);

        // DatePicker for selecting a date from the calendar
        DatePicker calendar = new DatePicker(LocalDate.now());
        calendar.setPrefSize(250,30);
        Calendar displayCalendar = new Calendar();

        // Adding all elements to the scheduleContainer
        scheduleContainer.getChildren().addAll(allSchedulePane, courseComboBox, calendar,displayCalendar);
        scheduleContainer.setAlignment(Pos.TOP_LEFT);

        // Right Pane: Today's Schedule Box
        VBox todayContainer = new VBox(0);
        todayContainer.setPrefSize(250,400);
        todayContainer.setStyle("-fx-border-width: 2; -fx-padding: 10;");
        todayContainer.setAlignment(Pos.CENTER_RIGHT);

        // Create Today's Schedule Box
        Rectangle todayScheduleBox = new Rectangle(250, 30);
        todayScheduleBox.setFill(Color.BLACK);

        // Label for "Today's Schedule"
        Label todayScheduleLabel = new Label("Today's Schedule");
        todayScheduleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane todaySchedulePane = new StackPane();
        todaySchedulePane.getChildren().addAll(todayScheduleBox, todayScheduleLabel);

        // Create Appointment Box
        Rectangle appointmentBox = new Rectangle(250, 30);
        appointmentBox.setFill(Color.BLACK);

        // Label for appointment
        Label appointmentLabel = new Label("Appointment");
        appointmentLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: white;");

        StackPane appointmentPane = new StackPane();
        appointmentPane.getChildren().addAll(appointmentBox, appointmentLabel);

        // Create Appointment space
        Rectangle appointmentList = new Rectangle(250, 340);
        appointmentList.setFill(Color.valueOf("#EFD9CE"));

        // Adding the schedule and appointment elements to the  todayContainer
        todayContainer.getChildren().addAll(todaySchedulePane, appointmentList, appointmentPane);

        // Set the left, center, and right sections of the page
        this.setLeft(scheduleContainer);
        this.setCenter(centerLayout);
        this.setRight(todayContainer);
        this.setStyle("-fx-background-color: #8A2BE2;"); // Purple background

        // Exit button action
        exitButton.setOnAction(e -> primaryStage.close());

        // Button actions for switching pages
        createButton.setOnAction(e -> Controller.createOfficeHour(primaryStage));
        viewButton.setOnAction(e -> Controller.viewOfficeHours(primaryStage));

    }

}
