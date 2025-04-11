package s25.cs151.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.time.LocalDate;

public class CreateSchedulePage extends BorderPane
{
    public CreateSchedulePage(Stage currentStage)
    {
        this.setStyle("-fx-background-color: #8A2BE2;");

        // Top banner
        Rectangle bannerBox = new Rectangle(800, 50);
        bannerBox.setFill(Color.valueOf("#B8B8FF"));

        Label titleLabel = new Label("Create Schedule");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        StackPane banner = new StackPane(bannerBox, titleLabel);
        banner.setAlignment(Pos.CENTER);
        this.setTop(banner);

        // Form layout
        GridPane form = new GridPane();
        form.setHgap(30);
        form.setVgap(30);
        form.setPadding(new Insets(60));
        form.setAlignment(Pos.CENTER);

        // Styling constant
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

// Time Slot
        Label timeSlotLabel = new Label("Time Slot *:");
        timeSlotLabel.setStyle(labelStyle);
        ComboBox<String> timeSlotBox = new ComboBox<>();
        timeSlotBox.getItems().addAll(
                "4:30 – 4:45", "4:45 – 5:00", "5:00 – 5:15", "5:15 – 5:30"
        );
        timeSlotBox.getSelectionModel().selectFirst();

// Course
        Label courseLabel = new Label("Course *:");
        courseLabel.setStyle(labelStyle);
        ComboBox<String> courseBox = new ComboBox<>();
        courseBox.getItems().addAll(
                "CS151-04", "CS146-01", "CS157A-03", "CS174-05"
        );
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

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #CAA8F5; -fx-text-fill: white; -fx-font-weight: bold;");
        saveButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showAlert("Validation Error", "Student's name is required.");
                return;
            }
//
            showAlert("Success", "Schedule saved successfully!");
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #CAA8F5; -fx-text-fill: white; -fx-font-weight: bold;");
        backButton.setOnAction(e -> {
            HomePage homePage = new HomePage(currentStage);
            currentStage.getScene().setRoot(homePage);
        });


        buttonBox.getChildren().addAll(saveButton, backButton);
        form.add(buttonBox, 1, 6);

        this.setCenter(form);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
