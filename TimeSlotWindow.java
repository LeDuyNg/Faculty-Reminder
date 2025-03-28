package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class creates the "Time Slot" page where faculty enter custom start and end times.
 */
public class TimeSlotWindow extends BorderPane {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    TimeSlotWindow(Stage currentStage) {
        super();

        //  window size
        currentStage.setWidth(900);
        currentStage.setHeight(600);

        // Left panel
        Rectangle leftBackground = new Rectangle(250, 600);
        leftBackground.setFill(Color.valueOf("#230C33"));

        Rectangle stickyNote = new Rectangle(200, 200);
        stickyNote.setFill(Color.valueOf("#F4ED5B"));
        stickyNote.setRotate(-5);

        Label titleLabel = new Label("Time Slots");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane pageName = new StackPane(leftBackground, stickyNote, titleLabel);
        pageName.setAlignment(Pos.CENTER);

        // Right panel
        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(650, 600);

        Rectangle bezel = new Rectangle(600, 400);
        bezel.setFill(Color.BLACK);

        Rectangle screen = new Rectangle(580, 380);
        screen.setFill(Color.valueOf("#DABFFF"));

        Rectangle monitorStand = new Rectangle(120, 180);
        monitorStand.setFill(Color.WHITE);

        StackPane monitorScreen = new StackPane(bezel, screen);
        monitorScreen.setAlignment(Pos.CENTER);

        VBox timeSlotContainer = new VBox(20);
        timeSlotContainer.setAlignment(Pos.CENTER);

        TitleBox timeSlotTitleBox = new TitleBox(200, 40, "Enter Time Slot", "#907AD6", "white");

        // Time input fields
        TextField fromHourField = new TextField();
        fromHourField.setPromptText("Start (HH:mm)");
        fromHourField.setPrefWidth(100); // Set preferred width
        fromHourField.setMaxWidth(100);  // Set maximum width

        TextField toHourField = new TextField();
        toHourField.setPromptText("End (HH:mm)");
        toHourField.setPrefWidth(100);
        toHourField.setMaxWidth(100);


        HBox timeFieldsContainer = new HBox(10, fromHourField, toHourField);
        timeFieldsContainer.setAlignment(Pos.CENTER);


        timeSlotContainer.getChildren().addAll(timeSlotTitleBox, fromHourField, toHourField);

        Button cancelButton = new CustomizeButton(180, 40, "Cancel", "#CAA8F5");
        Button saveButton = new CustomizeButton(200, 40, "Save", "#CAA8F5");

        HBox buttonContainer = new HBox(40, cancelButton, saveButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox formContainer = new VBox(30, timeSlotContainer, buttonContainer);
        formContainer.setAlignment(Pos.CENTER);

        monitorScreen.getChildren().add(formContainer);

        VBox monitorContainer = new VBox(monitorScreen, monitorStand);
        monitorContainer.setAlignment(Pos.CENTER);
        rightPane.setCenter(monitorContainer);

        this.setStyle("-fx-background-color: #8A2BE2;");
        this.setLeft(pageName);
        this.setRight(rightPane);

        cancelButton.setOnAction(e -> Controller.returnHomePage(currentStage));
        saveButton.setOnAction(e -> {
            String startTimeText = fromHourField.getText().trim();
            String endTimeText = toHourField.getText().trim();

            if (isValidTime(startTimeText) && isValidTime(endTimeText)) {
                LocalTime startTime = LocalTime.parse(startTimeText, TIME_FORMATTER);
                LocalTime endTime = LocalTime.parse(endTimeText, TIME_FORMATTER);

                if (startTime.isBefore(endTime)) {
                    String formattedSlot = startTime.format(TIME_FORMATTER) + " - " + endTime.format(TIME_FORMATTER);
                    Controller.saveTimeSlot(formattedSlot);
                    Controller.returnHomePage(currentStage);
                    CSVHandler.displayNotification("Time Slot Saved Successfully");
                } else {
                    CSVHandler.displayNotification("Error: Start time must be before end time.");
                }
            } else {
                CSVHandler.displayNotification("Invalid time format. Use HH:mm (e.g., 3:30).");
            }
        });
    }


    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time, TIME_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
