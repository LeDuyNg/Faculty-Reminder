package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class creates the "Create Office Hour" page
 */
public class CreateOfficeHourPage extends BorderPane {
    /**
     * Constructor to initialize the Create Office Hour Page.
     *
     * @param currentStage The current stage (window) that needs to be replaced with the "Create Office Hour" page.
     */
    CreateOfficeHourPage(Stage currentStage) {
        super();
        // Left side of the page
        // Creating the dark purple background for the left side
        Rectangle leftBackground = new Rectangle(180, 500);
        leftBackground.setFill(Color.valueOf("#230C33"));

        // Creating the sticky note with a yellow background
        Rectangle stickyNote = new Rectangle(160, 160);
        stickyNote.setFill(Color.valueOf("#F4ED5B"));

        // Rotate the sticky note -5 degrees
        stickyNote.setRotate(-5);

        // Creating the title label for Office Hours
        Label officeHour = new Label("Office Hours");
        officeHour.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Adding the background, sticky note, title label to StackPane
        StackPane pageName = new StackPane(leftBackground, stickyNote, officeHour);
        pageName.setAlignment(Pos.CENTER);

        // Right side of the page
        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(620, 500);

        // Creating the bezel
        Rectangle bezel = new Rectangle(580, 350);
        bezel.setFill(Color.BLACK);

        // Creating the screen
        Rectangle screen = new Rectangle(560, 330);
        screen.setFill(Color.valueOf("#DABFFF"));

        // Creating the stand for the monitor
        Rectangle monitorStand = new Rectangle(100, 150);
        monitorStand.setFill(Color.WHITE);

        // Grouping the bezel and screen together
        StackPane monitorScreen = new StackPane(bezel, screen);

        // Creating the Semester container
        VBox semesterContainer = new VBox();

        // Creating the Semester box
        TitleBox semesterTitleBox = new TitleBox(150,30,"Semester",
                "#907AD6", "white");

        // Creating the drop-down list for semester
        ComboBox<String> semesterComboBox = new ComboBox<>();
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterComboBox.setPrefSize(150,25);
        semesterComboBox.setValue("Spring"); // Default value is set to Spring
        semesterComboBox.setStyle("-fx-font-size: 16px; -fx-pref-width: 150px;");

        // Adding the semester box and the semester drop-down list to its container
        semesterContainer.getChildren().addAll(semesterTitleBox, semesterComboBox);
        semesterContainer.setAlignment(Pos.CENTER);

        // Creating the Year container
        VBox yearContainer = new VBox();
        yearContainer.setAlignment(Pos.CENTER);

        // Creating the Year box
        TitleBox yearTitleBox = new TitleBox(150,30,"Year",
                "#907AD6", "white");

        // Creating the Text Field for year
        TextField yearTextField = new TextField();
        yearTextField.setMaxWidth(150);
        yearTextField.setMaxHeight(30);
        yearTextField.clear();

        // Set the prompt for the year text field
        yearTextField.setPromptText("Enter a 4-digit integer");
        yearTextField.setStyle("-fx-prompt-text-fill: gray; -fx-background-color: white;");

        // Adding the year box and year text field to its container
        yearContainer.getChildren().addAll(yearTitleBox, yearTextField);

        // Creating the Day container
        VBox dayContainer = new VBox();
        dayContainer.setAlignment(Pos.CENTER);

        // Creating the Day box
        TitleBox dayTitleBox = new TitleBox(150,30,"Day",
                "#907AD6", "white");

        // Creating the checkboxes for days of the week
        CheckBox[] days = {new CheckBox("Monday"), new CheckBox("Tuesday"), new CheckBox("Wednesday"),
                new CheckBox("Thursday"), new CheckBox("Friday")};


        // GridPane for the check boxes
        GridPane checkBoxes = new GridPane();
        checkBoxes.setVgap(10);
        checkBoxes.setHgap(10);
        checkBoxes.setPrefSize(150, 150);
        checkBoxes.setAlignment(Pos.CENTER);
        for (int i = 0; i < days.length; i ++) {
            days[i].setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #4A90E2;");
            checkBoxes.add(days[i], 0, i);
        }

        // Creating the white background for the checkboxes
        Rectangle checkBoxesBackground = new Rectangle(150, 150);
        checkBoxesBackground.setFill(Color.WHITE);

        // Placing the checkboxes on top of the white background
        StackPane checkBoxPane = new StackPane(checkBoxesBackground, checkBoxes);
        checkBoxPane.setAlignment(Pos.CENTER);

        // Adding the Day box and the checkboxes to its container
        dayContainer.getChildren().addAll(dayTitleBox, checkBoxPane);

        // Creating the "Cancel" button
        Button cancelButton = new CustomizeButton(150, 30, "Cancel", "#CAA8F5");

        // Creating the "Save and Continue" button
        Button saveButton = new CustomizeButton(200, 30,
                "Save", "#CAA8F5");

        // Creating the screen layout and adding the containers for semester, year, and day selection
        GridPane screenLayout = new GridPane();
        screenLayout.setHgap(50);
        screenLayout.setVgap(50);
        screenLayout.setMaxHeight(320);
        screenLayout.setMaxWidth(530);
        screenLayout.add(semesterContainer, 0, 0);
        screenLayout.add(cancelButton, 0, 2);
        screenLayout.add(yearContainer, 1, 0);
        screenLayout.add(dayContainer, 2, 0);
        screenLayout.add(saveButton, 2, 2);


        // Adding the screen layout to the monitor screen and centering it
        monitorScreen.getChildren().add(screenLayout);
        monitorScreen.setAlignment(Pos.CENTER);

        // Creating a container for the monitor screen and the monitor stand
        VBox monitorContainer = new VBox();
        monitorContainer.setPrefSize(620, 500);
        monitorContainer.setAlignment(Pos.CENTER);
        monitorContainer.getChildren().addAll(monitorScreen,monitorStand);

        // Set the right side of the page to contain the monitor container
        rightPane.setCenter(monitorContainer);

        // Setting the background color and placing the left and right panes into the BorderPane
        this.setStyle("-fx-background-color: #8A2BE2;");
        this.setLeft(pageName);
        this.setRight(rightPane);

        // Setting the action for the Cancel button
        cancelButton.setOnAction(e-> Controller.returnHomePage(currentStage));

        // Save button action - Validate and save office hour details
        saveButton.setOnAction(e-> {
            try {
                boolean[] daysChosen = new boolean[5];
                for (int i = 0; i < daysChosen.length; i++) {
                    // Get the user's choice for days
                    daysChosen[i] = days[i].isSelected();
                }
                // Validate input fields before saving
                if (!CSVHandler.verifyYear(yearTextField.getText())) {// Check if valid year, 4-digit integer
                    CSVHandler.displayNotification("Invalid Year");// Display warning
                }
                else if (!CSVHandler.verifyChoseDay(daysChosen)) {// Check if day option left unchecked
                    CSVHandler.displayNotification("Must Choose At Least 1 Day");// Display warning
                }
                else if (CSVHandler.checkForDuplicate(semesterComboBox.getValue(), // Check for duplicate value of
                        Integer.parseInt(yearTextField.getText()))) {// combo semester, year
                    CSVHandler.displayNotification("Duplicate Office Hour Found");// Display warning
                }
                else {
                    // Save office hour if all checks pass
                    OfficeHour newOfficeHour = new OfficeHour(semesterComboBox.getValue(),
                            Integer.parseInt(yearTextField.getText()), daysChosen);

                    // Save new office hour to .\office_hour.csv file
                    Controller.saveOfficeHour(newOfficeHour);

                    // Return to home page
                    Controller.returnHomePage(currentStage);

                    // Display a notification indicating office hour saved successfully
                    CSVHandler.displayNotification("Save Successfully");
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
