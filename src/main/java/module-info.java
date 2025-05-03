module s25.cs151.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens s25.cs151.application to javafx.fxml;
    exports s25.cs151.application;
    exports s25.cs151.application.View;
    opens s25.cs151.application.View to javafx.fxml;
    exports s25.cs151.application.Model;
    opens s25.cs151.application.Model to javafx.fxml;
    exports s25.cs151.application.Controller;
    opens s25.cs151.application.Controller to javafx.fxml;
}