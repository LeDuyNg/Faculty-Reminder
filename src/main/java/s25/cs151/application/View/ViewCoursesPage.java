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
import s25.cs151.application.Model.Course;
import s25.cs151.application.Controller.CourseDAO;

import java.util.Collections;
import java.util.List;

/**
 * This class creates the "View Courses" page.
 */
public class ViewCoursesPage extends BorderPane
{
    /**
     * Constructs the View Courses page with a table displaying all added courses.
     *
     * @param currentStage current window to update when navigating between pages
     */
    public ViewCoursesPage(Stage currentStage)
    {
        super();
        DAOInt courseController = new CourseDAO();
        this.setStyle("-fx-background-color: #8A2BE2;");

        // Main content pane
        BorderPane rightPane = new BorderPane();
        rightPane.setPrefSize(800, 500);

        // Monitor style bezel and screen
        Rectangle bezel = new Rectangle(640, 400);
        bezel.setFill(Color.BLACK);

        Rectangle screen = new Rectangle(620, 380);
        screen.setFill(Color.valueOf("#DABFFF"));

        StackPane monitorScreen = new StackPane(bezel, screen);
        monitorScreen.setAlignment(Pos.CENTER);

        // Page title
        Label title = new Label("View Courses");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Table setup
        TableView<Course> courseTable = new TableView<>();
        courseTable.setPrefSize(560, 240);

        TableColumn<Course, String> codeCol = new TableColumn<>("Course Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        codeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Course, String> nameCol = new TableColumn<>("Course Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        nameCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Course, String> sectionCol = new TableColumn<>("Section");
        sectionCol.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        sectionCol.setStyle("-fx-alignment: CENTER;");

        courseTable.getColumns().addAll(codeCol, nameCol, sectionCol);
        courseTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        courseTable.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Course item, boolean empty) {
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


        List<Course> courses = courseController.load();
        Collections.sort(courses);
        courseTable.getItems().addAll(courses);

        // Back button
        Button backButton = new CustomizeButton(200, 30, "Back", "#CAA8F5");
        backButton.setOnAction(e -> Controller.returnHomePage(currentStage));

        // Layout
        VBox innerScreenLayout = new VBox(20, title, courseTable, backButton);
        innerScreenLayout.setAlignment(Pos.CENTER);
        innerScreenLayout.setMaxWidth(580);
        monitorScreen.getChildren().add(innerScreenLayout);

        // Monitor stand
        Rectangle monitorStand = new Rectangle(100, 150);
        monitorStand.setFill(Color.WHITE);

        VBox monitorContainer = new VBox(monitorScreen, monitorStand);
        monitorContainer.setAlignment(Pos.CENTER);

        rightPane.setCenter(monitorContainer);
        this.setCenter(rightPane);
    }
}
