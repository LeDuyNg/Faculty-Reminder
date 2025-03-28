package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This class creates the "Course" page.
 */
public class CourseWindow extends BorderPane {
    CourseWindow(Stage currentStage) {
        super();

        // Window size
        currentStage.setWidth(900);
        currentStage.setHeight(600);

        // Left panel
        Rectangle leftBackground = new Rectangle(250, 600);
        leftBackground.setFill(Color.valueOf("#230C33"));

        Rectangle stickyNote = new Rectangle(200, 200);
        stickyNote.setFill(Color.valueOf("#F4ED5B"));
        stickyNote.setRotate(-5);

        Label titleLabel = new Label("Course Details");
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

        VBox courseContainer = new VBox(20);
        courseContainer.setAlignment(Pos.CENTER);

        TitleBox courseTitleBox = new TitleBox(200, 40, "Enter Course Details", "#907AD6", "white");

        TextField courseNumber = new TextField();
        courseNumber.setPromptText("Course Number");
        courseNumber.setMaxWidth(250);

        TextField courseName = new TextField();
        courseName.setPromptText("Course Name");
        courseName.setMaxWidth(250);

        TextField courseSection = new TextField();
        courseSection.setPromptText("Section");
        courseSection.setMaxWidth(250);

        courseContainer.getChildren().addAll(courseTitleBox, courseNumber, courseName, courseSection);

        Button cancelButton = new CustomizeButton(180, 40, "Cancel", "#CAA8F5");
        Button saveButton = new CustomizeButton(200, 40, "Save", "#CAA8F5");

        HBox buttonContainer = new HBox(40, cancelButton, saveButton);
        buttonContainer.setAlignment(Pos.CENTER);

        VBox formContainer = new VBox(30, courseContainer, buttonContainer);
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
            String number = courseNumber.getText();
            String name = courseName.getText();
            String section = courseSection.getText();
            if (number.isEmpty() || name.isEmpty() || section.isEmpty()) {
                CSVHandler.displayNotification("Please fill all fields.");
            } else {
                Controller.saveCourseDetails(number, name, section);
                Controller.returnHomePage(currentStage);
                CSVHandler.displayNotification("Course Saved Successfully");
            }
        });
    }
}
