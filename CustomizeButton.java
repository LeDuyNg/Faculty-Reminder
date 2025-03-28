package s25.cs151.application;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
/**
 * This class extends the Button class to create a customized button that fits the needs of the programmer
 */
public class CustomizeButton extends Button {
    CustomizeButton(int width, int height, String buttonName, String buttonColor) {
        super(buttonName);
        // Creating background for the buttons
        BackgroundFill buttonBackground = new BackgroundFill(Color.valueOf(buttonColor), new CornerRadii(5), null);

        // Set the dimensions for the button
        this.setPrefSize(width, height);

        // Change the color of the button the chosen color
        this.setBackground(new Background(buttonBackground));

        // Formatting the text style of the button
        this.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
    }
}
