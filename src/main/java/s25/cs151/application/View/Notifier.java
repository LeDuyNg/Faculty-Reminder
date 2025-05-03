package s25.cs151.application.View;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import s25.cs151.application.Controller.Controller;

/**
 * The Notifier class extends BorderPane to create a simple notification popup.
 * It displays a message and an "OK" button to close the notification.
 */
public class Notifier extends BorderPane {
    /**
     * Constructs a Notifier instance.
     *
     * @param message The message to be displayed in the notification.
     * @param notificationStage The Stage representing the notification window.
     */
    public Notifier(String message, Stage notificationStage) {
        // Set the preferred size of the notification window
        this.setPrefSize(300, 300);

        // Create a label to display the message
        Label msg = new Label(message);

        // Create an "OK" button with custom styling
        CustomizeButton okButton = new CustomizeButton(50, 30, "OK", "#ffffff");

        // Create a VBox container for message and button, with spacing and alignment
        VBox msgContainer = new VBox(msg, okButton);
        msgContainer.setSpacing(20);
        msgContainer.setAlignment(Pos.CENTER);

        // Set the VBox as the center of the BorderPane layout
        this.setCenter(msgContainer);

        // Set the button action to close the notification stage
        okButton.setOnAction(e-> Controller.closeNotification(notificationStage));
    }
}
