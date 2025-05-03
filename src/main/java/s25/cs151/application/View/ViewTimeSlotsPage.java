package s25.cs151.application.View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import s25.cs151.application.Controller.CSVHandler;
import s25.cs151.application.Controller.Controller;
import s25.cs151.application.Controller.ModelControllerInt;
import s25.cs151.application.Controller.TimeSlotDAL;
import s25.cs151.application.Model.TimeSlot;

import java.util.Collections;
import java.util.List;

/**
 * This class creates the "View Office Hour" page
 */
public class ViewTimeSlotsPage extends BorderPane
{
    /**
     * Constructs View Office Hours page with a monitor layout containing list of saved office hours
     *
     * @param currentStage current window to update when navigating between pages
     */
    public ViewTimeSlotsPage(Stage currentStage)
    {
        super();
        this.setStyle("-fx-background-color: #8A2BE2;");

        // Main content pane
        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(800, 500);

        // Monitor style bezel, screen
        Rectangle bezel = new Rectangle(640, 400);
        bezel.setFill(Color.BLACK);

        Rectangle screen = new Rectangle(620, 380);
        screen.setFill(Color.valueOf("#DABFFF"));

        StackPane monitorScreen = new StackPane(bezel, screen);
        monitorScreen.setAlignment(Pos.CENTER);

        // Time Slots title
        Label listTitle = new Label("View Time Slots");
        listTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Time Slots table
        TableView<TimeSlot> table = new TableView<>();
        table.setPrefSize(560, 240);

        TableColumn<TimeSlot, String> startTimeCol = new TableColumn<>("From Hour");
        startTimeCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            int startTimeInMinutes = cellData.getValue().getStartTimeInMinutes();
            return cellData.getValue().formatTimetoString(startTimeInMinutes);
        }));
        startTimeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<TimeSlot, String> endTimeCol = new TableColumn<>("To Hour");
        endTimeCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            int endTimeInMinutes = cellData.getValue().getEndTimeInMinutes();
            return cellData.getValue().formatTimetoString(endTimeInMinutes);
        }));
        endTimeCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(startTimeCol, endTimeCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: black;" +
                        "-fx-selection-bar: #CAA8F5;" +
                        "-fx-selection-bar-text: black;"
        );

        table.setRowFactory(tv -> new TableRow<>()
        {
            protected void updateItem(TimeSlot item, boolean empty)
            {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else if (getIndex() % 2 == 0) {
                    setStyle("-fx-background-color: #E6D0FA;");
                } else {
                    setStyle("-fx-background-color: #F6EDFF;");
                }
            }
        });

        ModelControllerInt<TimeSlot> timeSlotController = new TimeSlotDAL();
        List<TimeSlot> formattedTimeSlots = timeSlotController.loadModelObjects();
        Collections.sort(formattedTimeSlots);
        table.getItems().addAll(formattedTimeSlots);

        // Back button
        Button backButton = new CustomizeButton(200, 30, "Back", "#CAA8F5");
        backButton.setOnAction(e -> Controller.returnHomePage(currentStage));

        // Inner container (smaller, to fit inside screen)
        VBox innerScreenLayout = new VBox(20, listTitle, table, backButton);
        innerScreenLayout.setAlignment(Pos.CENTER);
        innerScreenLayout.setMaxWidth(580);

        // Add inner layout to monitor screen
        monitorScreen.getChildren().add(innerScreenLayout);

        // Monitor stand
        Rectangle monitorStand = new Rectangle(100, 150);
        monitorStand.setFill(Color.WHITE);

        // Full right container (monitor and stand)
        VBox monitorContainer = new VBox(monitorScreen, monitorStand);
        monitorContainer.setAlignment(Pos.CENTER);

        rightPane.setCenter(monitorContainer);
        this.setCenter(rightPane);
    }
}
