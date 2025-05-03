package s25.cs151.application.View;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import s25.cs151.application.Controller.*;
import s25.cs151.application.Model.Course;
import s25.cs151.application.Model.OfficeHourSchedule;
import s25.cs151.application.Model.TimeSlot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * EditSchedulePage allows users to search for and modify office hour schedule records
 */
public class EditSchedulePage extends BorderPane {
    /**
     * Constructs EditSchedulePage with a searchable table of office hour entries
     * Provides interface to edit existing entries and update them in CSV file
     *
     * @param currentStage current window stage to return or update views
     */
    public EditSchedulePage(Stage currentStage) {
        ModelControllerInt<TimeSlot> timeSlotController = new TimeSlotDAL();
        OfficeHourScheduleDAL officeHourScheduleController = new OfficeHourScheduleDAL();
        ModelControllerInt<Course> courseController = new CourseDAL();


        this.setStyle("-fx-background-color: #8A2BE2;");

        Label title = new Label("Edit Office Hour Schedule");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane titleBanner = new StackPane(title);
        titleBanner.setAlignment(Pos.CENTER);
        titleBanner.setPadding(new Insets(10));
        titleBanner.setStyle("-fx-background-color: #CAA8F5; -fx-border-color: white; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        TableView<OfficeHourSchedule> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(1000);
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");

        TableColumn<OfficeHourSchedule, String> dateCol = new TableColumn<>("Schedule Date");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getScheduleDate()));
        dateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> timeSlotCol = new TableColumn<>("Time Slot");
        timeSlotCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTimeSlot().formatTimeSlottoString()));
        timeSlotCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(cell -> {
            Course course = cell.getValue().getCourse();
            return new SimpleStringProperty(course.courseIDtoString() + "-" + course.getCourseName());
        });
        courseCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> studentCol = new TableColumn<>("Student Name");
        studentCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudentName()));
        studentCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReason()));
        reasonCol.setPrefWidth(150);
        reasonCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getComment()));
        commentCol.setPrefWidth(200);
        commentCol.setStyle("-fx-alignment: CENTER;");


        ObservableList<OfficeHourSchedule> schedules = FXCollections.observableArrayList(officeHourScheduleController.load());
        FilteredList<OfficeHourSchedule> filteredSchedules = new FilteredList<>(schedules, p -> true);
        FXCollections.sort(schedules);
        FXCollections.reverse(schedules);

        table.getColumns().addAll(dateCol, timeSlotCol, courseCol, studentCol, reasonCol, commentCol);
        table.setItems(filteredSchedules);

        CustomizeButton returnButton = new CustomizeButton(200, 40, "Return to Home Page", "#CAA8F5");
        returnButton.setOnAction(event -> Controller.returnHomePage(currentStage));

        CustomizeButton editButton = new CustomizeButton(200, 40, "Edit Selected", "#CAA8F5");
        editButton.setOnAction(event -> {
            OfficeHourSchedule selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Stage popup = new Stage();
                popup.setTitle("Edit Schedule");

                TextField studentField = new TextField(selected.getStudentName());
                DatePicker datePicker = new DatePicker(LocalDate.parse(selected.getScheduleDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                TextField reasonField = new TextField(selected.getReason());
                TextField commentField = new TextField(selected.getComment());

                ComboBox<TimeSlot> timeSlotBox = new ComboBox<>(FXCollections.observableArrayList(timeSlotController.load()));
                timeSlotBox.setValue(selected.getTimeSlot());

                ComboBox<Course> courseBox = new ComboBox<>(FXCollections.observableArrayList(courseController.load()));
                courseBox.setValue(selected.getCourse());

                Label errorLabel = new Label();
                errorLabel.setStyle("-fx-text-fill: red;");

                CustomizeButton saveButton = new CustomizeButton(150, 30, "Save", "#CAA8F5");
                saveButton.setOnAction(ev -> {
                    try {
                        selected.setStudentName(studentField.getText());
                        selected.setScheduleDate(datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                        selected.setReason(reasonField.getText());
                        selected.setComment(commentField.getText());
                        selected.setTimeSlot(timeSlotBox.getValue());
                        selected.setCourse(courseBox.getValue());

                        officeHourScheduleController.rewriteAllSchedules(schedules);
                        table.setItems(FXCollections.observableArrayList(officeHourScheduleController.load()));
                        table.refresh();
                        popup.close();

                    } catch (Exception ex) {
                        errorLabel.setText("Failed to save: " + ex.getMessage());
                    }
                });

                VBox form = new VBox(10,
                        new Label("Student:"), studentField,
                        new Label("Date (MM/DD/YYYY):"), datePicker,
                        new Label("Time Slot:"), timeSlotBox,
                        new Label("Course:"), courseBox,
                        new Label("Reason:"), reasonField,
                        new Label("Comment:"), commentField,
                        saveButton, errorLabel);

                form.setPadding(new Insets(20));
                Scene scene = new Scene(form, 400, 600);
                popup.setScene(scene);
                popup.setResizable(false);
                popup.show();
            } else {
                Controller.displayNotification("No schedule selected");
            }
        });

        TextField searchBar = new TextField();
        searchBar.setPromptText("Enter student's name");
        searchBar.setPrefSize(600, 30);
        CustomizeButton searchButton = new CustomizeButton(100, 20, "Search", "#CAA8F5");
        searchButton.setOnAction(event -> {
            String query = searchBar.getText().toLowerCase();
            filteredSchedules.setPredicate(schedule ->
                    query.isEmpty() || schedule.getStudentName().toLowerCase().contains(query));
        });

        HBox searchContainer = new HBox(20, searchBar, searchButton);
        searchContainer.setAlignment(Pos.CENTER);

        VBox layout = new VBox(20, titleBanner, searchContainer, table, editButton, returnButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #8A2BE2;");

        this.setCenter(layout);
    }
}

