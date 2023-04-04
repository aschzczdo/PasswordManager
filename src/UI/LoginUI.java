package UI;

import UI.RegisterUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.Login;

public class LoginUI extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        usernameField = new TextField();
        passwordField = new PasswordField();
        statusLabel = new Label();

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(loginButton, registerButton);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(gridPane, hBox, statusLabel);
        vBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vBox, 350, 250);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegister(primaryStage));
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (Login.loginDB(username, password)) {
            // Login successful, switch to the password dashboard.
            ui.PasswordDashboard dashboard = new ui.PasswordDashboard();
            try {
                dashboard.start(new Stage());
                ((Stage) usernameField.getScene().getWindow()).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            statusLabel.setText("Login failed. Please check your credentials.");
        }
    }


    private void handleRegister(Stage primaryStage) {
        RegisterUI registerUI = new RegisterUI();
        try {
            registerUI.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
