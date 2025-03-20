package s25.cs151.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class creates the "View Office Hour" page
 */
    public class ViewOfficeHoursPage extends VBox {

        /**
         * Initializes View Office Hours Page
         * @param currentStage the current window
         */
        public ViewOfficeHoursPage(Stage currentStage) {
            super(15);
            this.setAlignment(Pos.CENTER);
            this.setStyle("-fx-background-color: #D8BFD8;");

            Label titleLabel = new Label("Office Hours");
            titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

            ListView<String> officeHourListView = new ListView<>();
            ObservableList<String> officeHours = CSVHandler.loadOfficeHoursFromCSV();
            officeHourListView.setItems(officeHours);

            Button backButton = new Button("Back");
            backButton.setOnAction(e -> Controller.returnHomePage(currentStage));

            this.getChildren().addAll(titleLabel, officeHourListView, backButton);
        }
    }