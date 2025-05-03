package s25.cs151.application.View;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import s25.cs151.application.Controller.Controller;
import s25.cs151.application.Controller.ModelControllerInt;
import s25.cs151.application.Controller.TimeSlotDAL;
import s25.cs151.application.Model.TimeSlot;

/**
 * This class creates the "Time Slot" page where faculty can enter custom start and end times.
 */
public class CreateTimeSlotPage extends BorderPane {

    public CreateTimeSlotPage(Stage currentStage) {
        super();

        ModelControllerInt<TimeSlot> timeSlotController = new TimeSlotDAL();

        // Set window dimensions
        currentStage.setWidth(900);
        currentStage.setHeight(600);

        // -------------------- Left Panel UI --------------------
        // Background rectangle for the left panel
        Rectangle leftBackground = new Rectangle(250, 600);
        leftBackground.setFill(Color.valueOf("#230C33"));

        // "Sticky note" visual element
        Rectangle stickyNote = new Rectangle(200, 200);
        stickyNote.setFill(Color.valueOf("#F4ED5B"));
        stickyNote.setRotate(-5); // Tilt for effect

        // Title label on sticky note
        Label titleLabel = new Label("Time Slots");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane pageName = new StackPane(leftBackground, stickyNote, titleLabel);
        pageName.setAlignment(Pos.CENTER);

        // -------------------- Right Panel UI --------------------
        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(650, 600);

        // Components to represent a "monitor" layout
        Rectangle bezel = new Rectangle(600, 400);
        bezel.setFill(Color.BLACK);

        Rectangle screen = new Rectangle(580, 380);
        screen.setFill(Color.valueOf("#DABFFF"));

        Rectangle monitorStand = new Rectangle(120, 180);
        monitorStand.setFill(Color.WHITE);

        StackPane monitorScreen = new StackPane(bezel, screen);
        monitorScreen.setAlignment(Pos.CENTER);

        VBox timeSlotContainer = new VBox(20);
        timeSlotContainer.setMaxWidth(Region.USE_PREF_SIZE);
        timeSlotContainer.setAlignment(Pos.CENTER);

        // -------------------- Start Time Section --------------------
        HBox startTimeContainer = new HBox(0);

        // Custom title box for start time
        TitleBox startTimeTitle = new TitleBox(100, 50, "From Hour", "#907AD6", "white");

        // ComboBoxes for selecting hour and minute
        ComboBox<Integer> startHourComboBox = new ComboBox<>();
        startHourComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23);
        startHourComboBox.setPromptText("Hour");
        startHourComboBox.setPrefSize(100, 50);
        startHourComboBox.setStyle("-fx-font-size: 12px; -fx-pref-width: 100px;");

        ComboBox<Integer> startMinuteComboBox = new ComboBox<>();
        startMinuteComboBox.getItems().addAll(0, 15, 30, 45);
        startMinuteComboBox.setPromptText("Minute");
        startMinuteComboBox.setPrefSize(100, 50);
        startMinuteComboBox.setStyle("-fx-font-size: 12px; -fx-pref-width: 100px;");

        startTimeContainer.getChildren().addAll(startTimeTitle, startHourComboBox, startMinuteComboBox);

        // -------------------- End Time Section --------------------
        HBox endTimeContainer = new HBox(0);

        // Custom title box for end time
        TitleBox endTimeTitle = new TitleBox(100, 50, "To Hour", "#907AD6", "white");

        // ComboBoxes for selecting hour and minute
        ComboBox<Integer> endHourComboBox = new ComboBox<>();
        endHourComboBox.getItems().addAll(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
                18, 19, 20, 21, 22, 23);
        endHourComboBox.setPromptText("Hour");
        endHourComboBox.setPrefSize(100, 50);
        endHourComboBox.setStyle("-fx-font-size: 12px; -fx-pref-width: 100px;");

        ComboBox<Integer> endMinuteComboBox = new ComboBox<>();
        endMinuteComboBox.getItems().addAll(0, 15, 30, 45);
        endMinuteComboBox.setPromptText("Minute");
        endMinuteComboBox.setPrefSize(100, 50);
        endMinuteComboBox.setStyle("-fx-font-size: 12px; -fx-pref-width: 100px;");

        endTimeContainer.getChildren().addAll(endTimeTitle, endHourComboBox, endMinuteComboBox);

        // Add both time sections to the container
        timeSlotContainer.getChildren().addAll(startTimeContainer, endTimeContainer);

        // -------------------- Buttons --------------------
        Button cancelButton = new CustomizeButton(180, 40, "Cancel", "#CAA8F5");
        Button saveButton = new CustomizeButton(200, 40, "Save", "#CAA8F5");

        HBox buttonContainer = new HBox(40, cancelButton, saveButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox formContainer = new VBox(30, timeSlotContainer, buttonContainer);
        formContainer.setAlignment(Pos.CENTER);

        monitorScreen.getChildren().addAll(formContainer);

        // Add screen and stand to monitor container
        VBox monitorContainer = new VBox(monitorScreen, monitorStand);
        monitorContainer.setAlignment(Pos.CENTER);
        rightPane.setCenter(monitorContainer);

        // Final layout assembly
        this.setStyle("-fx-background-color: #8A2BE2;");
        this.setLeft(pageName);
        this.setRight(rightPane);

        // -------------------- Event Handling --------------------
        // Cancel returns to home page
        cancelButton.setOnAction(e -> Controller.returnHomePage(currentStage));

        // Save button validates and stores time slot
        saveButton.setOnAction(e -> {
            Integer startHour = startHourComboBox.getValue();
            Integer startMinute = startMinuteComboBox.getValue();
            Integer endHour = endHourComboBox.getValue();
            Integer endMinute = endMinuteComboBox.getValue();



            // Validate that all fields are selected
            if (startHour == null || startMinute == null || endHour == null || endMinute == null) {
                Controller.displayNotification("Must select Start Time and End Time");
            } else {
                try {
                    // Create and save the TimeSlot object
                    TimeSlot newTimeSlot = new TimeSlot(startHour, startMinute, endHour, endMinute);
                    timeSlotController.save(newTimeSlot);
                    Controller.returnHomePage(currentStage);
                    Controller.displayNotification("Time Slot Saved Successfully");
                } catch (Exception exception) {
                    // Display error if invalid time
                    Controller.displayNotification(exception.getMessage());
                }
            }
        });
    }
}
