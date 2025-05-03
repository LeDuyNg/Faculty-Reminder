package s25.cs151.application.View;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import s25.cs151.application.Controller.Controller;
import s25.cs151.application.Controller.DAOInt;
import s25.cs151.application.Controller.OfficeHourDAO;
import s25.cs151.application.Model.OfficeHour;

import java.util.Collections;
import java.util.List;

/**
 * This class creates the "View Office Hour" page
 */
public class ViewOfficeHoursPage extends BorderPane
{
    /**
     * Constructs View Office Hours page with a monitor layout containing list of saved office hours
     *
     * @param currentStage current window to update when navigating between pages
     */
    public ViewOfficeHoursPage(Stage currentStage)
    {
        super();
        DAOInt officeHourController = new OfficeHourDAO();
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

        // Office Hours title
        Label listTitle = new Label("View Office Hours");
        listTitle.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Office Hour table
        TableView<OfficeHour> table = new TableView<>();
        table.setPrefSize(560, 240);

        TableColumn<OfficeHour, String> semesterCol = new TableColumn<>("Semester");
        semesterCol.setCellValueFactory(new PropertyValueFactory<>("semester"));
        semesterCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHour, Integer> yearCol = new TableColumn<>("Year");
        yearCol.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<OfficeHour, String> daysCol = new TableColumn<>("Days");
        daysCol.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> {
            boolean[] days = cellData.getValue().getDays();
            String[] names = {"Mon", "Tue", "Wed", "Thu", "Fri"};
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < days.length; i++) {
                if (days[i]) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(names[i]);
                }
            }
            return sb.toString();
        }));
        daysCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(semesterCol, yearCol, daysCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-text-fill: black;" +
                        "-fx-selection-bar: #CAA8F5;" +
                        "-fx-selection-bar-text: black;"
        );

        table.setRowFactory(tv -> new TableRow<>()
        {
            protected void updateItem(OfficeHour item, boolean empty)
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

        // Load office hours from CSV and sort them

        List<OfficeHour> formattedOfficeHours = officeHourController.load();
        Collections.sort(formattedOfficeHours);
        table.getItems().addAll(formattedOfficeHours);

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
