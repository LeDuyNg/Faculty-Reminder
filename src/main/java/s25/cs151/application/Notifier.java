package s25.cs151.application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Notifier extends BorderPane {
    public Notifier(String message, Stage notificationStage) {
        this.setPrefSize(300, 300);
        Label msg = new Label(message);
        CustomizeButton okButton = new CustomizeButton(50, 30, "OK", "#ffffff");
        VBox msgContainer = new VBox(msg, okButton);
        msgContainer.setAlignment(Pos.CENTER);
        this.setCenter(msgContainer);

        okButton.setOnAction(e->Controller.closeNotification(notificationStage));
    }
}
