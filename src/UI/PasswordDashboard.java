package UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PasswordDashboard {
    public static Scene createDashboardScene() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label welcomeLabel = new Label("Welcome to your password dashboard!");
        vbox.getChildren().addAll(welcomeLabel);

        // Add more UI components to the dashboard here

        return new Scene(vbox, 600, 400);
    }
}
