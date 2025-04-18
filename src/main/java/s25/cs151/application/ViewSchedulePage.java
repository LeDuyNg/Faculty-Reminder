package s25.cs151.application;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * ViewSchedulePage displays table of scheduled office hours, including date, time slot, course, student's name, reason, and comment
 * Data is loaded from CSV file and then sorted
 */
public class ViewSchedulePage extends BorderPane
{
    /**
     * Creates ViewSchedulePage and populates it with table view of OfficeHourSchedule objects
     *
     * @param currentStage The current window stage to return to home from
     */
    public ViewSchedulePage(Stage currentStage)
    {
        this.setStyle("-fx-background-color: #8A2BE2;");
        Label title = new Label("View Office Hour Schedule");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: black;");

        StackPane titleBanner = new StackPane(title);
        titleBanner.setAlignment(Pos.CENTER);
        titleBanner.setPadding(new Insets(10));
        titleBanner.setStyle(
                "-fx-background-color: #CAA8F5;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 3px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-background-radius: 10px;"
        );

        TableView<OfficeHourSchedule> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setPrefWidth(1000);
        table.setPrefHeight(500);
        table.setStyle("-fx-background-color: white; -fx-background-radius: 10px;");
        table.setRowFactory(tv -> new TableRow<>()
        {
            protected void updateItem(OfficeHourSchedule item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (getIndex() % 2 == 0) {
                    setStyle("-fx-background-color: #F1E3FF;");
                } else {
                    setStyle("-fx-background-color: #FFFFFF;");
                }
            }
        });

        TableColumn<OfficeHourSchedule, String> dateCol = new TableColumn<>("Schedule Date");
        dateCol.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getScheduleDate()));
        dateCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> timeSlotCol = new TableColumn<>("Time Slot");
        timeSlotCol.setCellValueFactory(cell -> {
            TimeSlot ts = cell.getValue().getTimeSlot();
            return new SimpleStringProperty(ts.formatTimeSlottoString());
        });
        timeSlotCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHourSchedule, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(cell -> {
            Course course = cell.getValue().getCourse();
            return new SimpleStringProperty(String.format("%s-%s", course.courseIDtoString(), course.getCourseName()));
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

        table.getColumns().addAll(dateCol, timeSlotCol, courseCol, studentCol, reasonCol, commentCol);
        ObservableList<OfficeHourSchedule> schedules = FXCollections.observableArrayList(
                CSVHandler.loadOfficeHourScheduleObjects()
        );
        FXCollections.sort(schedules);
        table.setItems(schedules);

        CustomizeButton returnButton = new CustomizeButton(200, 40, "Return to Home Page", "#CAA8F5");
        returnButton.setOnAction(e -> Controller.returnHomePage(currentStage));

        VBox layout = new VBox(20, titleBanner, table, returnButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #8A2BE2;");
        this.setCenter(layout);
    }

    /**
     * Converts minutes to hh:mm format
     *
     * @param totalMinutes time in minutes
     * @return formatted time string
     */
    private String formatTime(int totalMinutes)
    {
        return LocalTime.MIN.plusMinutes(totalMinutes).format(DateTimeFormatter.ofPattern("hh:mm a"));
    }
}