package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ViewSchedulePage extends BorderPane
{
    public ViewSchedulePage(Stage currentStage)
    {
        this.setStyle("-fx-background-color: #8A2BE2;");

        Rectangle bannerBox = new Rectangle(800, 50);
        bannerBox.setFill(Color.valueOf("#B8B8FF"));

        Label titleLabel = new Label("View Schedule");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        StackPane banner = new StackPane(bannerBox, titleLabel);
        banner.setAlignment(Pos.CENTER);

        this.setTop(banner);
    }
}