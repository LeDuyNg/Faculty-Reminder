package s25.cs151.application;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This class extends the StackPane class to create a customized title box
 */
public class TitleBox extends StackPane {
    TitleBox(int width, int height, String boxName, String boxColor, String textColor) {
        super();
        // Create the background for the box with the specified width and height
        Rectangle box = new Rectangle(width, height);

        // Changes the color of the box
        box.setFill(Color.valueOf(boxColor));

        // Name of the box
        Label boxLabel = new Label(boxName);

        // Set the style for the name of the box
        boxLabel.setStyle(String.format("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: %s;", textColor));

        // Creating the box
        this.getChildren().addAll(box, boxLabel);
    }
}
