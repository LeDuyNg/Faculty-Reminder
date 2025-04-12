package s25.cs151.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CreateSchedulePage extends BorderPane
{
    /**
     * Constructs the CreateSchedulePage layout with all necessary form fields.
     * Initializes UI elements like labels, text fields, combo boxes, and buttons for the schedule creation.
     *
     * @param currentStage The current stage (window) of the application.
     */
    public CreateSchedulePage(Stage currentStage)
    {
        this.setStyle("-fx-background-color: #8A2BE2;");

        // Top banner with title "Create Schedule"
        Rectangle bannerBox = new Rectangle(800, 50);
        bannerBox.setFill(Color.valueOf("#B8B8FF"));

        Label titleLabel = new Label("Create Schedule");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        StackPane banner = new StackPane(bannerBox, titleLabel);
        banner.setAlignment(Pos.CENTER);
        this.setTop(banner);

        // Form layout with padding, gaps, and centered content
        GridPane form = new GridPane();
        form.setHgap(30);
        form.setVgap(30);
        form.setPadding(new Insets(60));
        form.setAlignment(Pos.CENTER);

        // Styling for labels
        String labelStyle = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;";

        // Student Full Name
        Label nameLabel = new Label("Student's Full Name *:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField();
        nameField.setPromptText("John Doe");

        // Schedule Date
        Label dateLabel = new Label("Schedule Date *:");
        dateLabel.setStyle(labelStyle);
        DatePicker datePicker = new DatePicker(LocalDate.now());

        // Create the formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Time Slot
        Label timeSlotLabel = new Label("Time Slot *:");
        timeSlotLabel.setStyle(labelStyle);

        // Get all Time Slots from database
        List<TimeSlot> timeSlots = CSVHandler.loadTimeSlotObjects();

        // Format the Time Slot objects and store them as String in array list
        ArrayList<String> formattedTimeSlots = new ArrayList<>();

        for (TimeSlot timeSlot : timeSlots) {
            formattedTimeSlots.add(timeSlot.formatTimeSlottoString());
        }
        ComboBox<String> timeSlotBox = new ComboBox<>();
        timeSlotBox.getItems().addAll(formattedTimeSlots);
        timeSlotBox.getSelectionModel().selectFirst();

        // Course
        Label courseLabel = new Label("Course *:");
        courseLabel.setStyle(labelStyle);

        // Get all Course objects from the database and format them for ComboBox
        List<Course> courses = CourseHandler.loadCoursesFromCSV();

        ArrayList<String> courseIDs = new ArrayList<>();
        for (Course course : courses) {
            courseIDs.add(course.courseIDtoString());
        }

        ComboBox<String> courseBox = new ComboBox<>();
        courseBox.getItems().addAll(courseIDs);
        courseBox.getSelectionModel().selectFirst();

        // Reason (optional)
        Label reasonLabel = new Label("Reason:");
        reasonLabel.setStyle(labelStyle);
        TextField reasonField = new TextField();
        reasonField.setPromptText("Optional");

        // Comment (optional)
        Label commentLabel = new Label("Comment:");
        commentLabel.setStyle(labelStyle);
        TextField commentField = new TextField();
        commentField.setPromptText("Optional");

        // Add form fields
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(dateLabel, 0, 1);
        form.add(datePicker, 1, 1);
        form.add(timeSlotLabel, 0, 2);
        form.add(timeSlotBox, 1, 2);
        form.add(courseLabel, 0, 3);
        form.add(courseBox, 1, 3);
        form.add(reasonLabel, 0, 4);
        form.add(reasonField, 1, 4);
        form.add(commentLabel, 0, 5);
        form.add(commentField, 1, 5);

        // Button HBox (Save + Back)
        HBox buttonBox = new HBox(5);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(5));

        // Save Button - saves the schedule and shows confirmation
        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #CAA8F5; -fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            try {
                // Get the time slot, schedule date, reason, comment, and course from form inputs
                TimeSlot timeSlot = new TimeSlot(timeSlotBox.getValue());
                String scheduleDate = datePicker.getValue().format(formatter);
                String reason = reasonField.getText().trim();
                String comment = commentField.getText().trim();
                Course course = null;
                for (int i = 0; i < courseIDs.size(); i ++) {
                    if (courses.get(i).courseIDtoString().equalsIgnoreCase(courseBox.getValue())) {
                        course = courses.get(i);
                    }
                }
                // Validate the student name before saving
                if (name.isEmpty()) {
                    showAlert("Validation Error", "Student's name is required.");
                    return;
                }
                // Create the schedule and save it
                OfficeHourSchedule schedule = new OfficeHourSchedule(name, scheduleDate,
                        timeSlot, course, reason, comment);
                Controller.saveSchedule(schedule);
                showAlert("Success", "Schedule saved successfully!");
                Controller.returnHomePage(currentStage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Back Button - returns to the home page
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #CAA8F5; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> Controller.returnHomePage(currentStage));

        // Add the buttons to the layout
        buttonBox.getChildren().addAll(saveButton, backButton);
        form.add(buttonBox, 1, 6);

        // Set the form as the center of the page
        this.setCenter(form);
    }

    /**
     * Displays an alert with the specified title and message.
     *
     * @param title The title of the alert window.
     * @param message The message to display in the alert window.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
