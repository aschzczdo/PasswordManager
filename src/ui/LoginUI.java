package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import user.Login;
import user.User;

public class LoginUI extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        usernameField = new TextField();
        passwordField = new PasswordField();
        statusLabel = new Label();
        statusLabel.getStyleClass().add("status-label");

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("login-button");
        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("register-button");
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(loginButton, registerButton);
        hBox.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(10);
        vBox.getStyleClass().add("vbox");
        vBox.getChildren().addAll(gridPane, hBox, statusLabel);
        vBox.setAlignment(Pos.CENTER);

        Button closeButton = new Button("X");
        closeButton.getStyleClass().add("close-button");
        closeButton.setOnAction(e -> primaryStage.close());
        VBox.setMargin(closeButton, new Insets(0, 0, 0, 330));
        vBox.getChildren().add(closeButton);

        Scene scene = new Scene(vBox, 350, 250);
        scene.getStylesheets().add("resources/styles.css");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Login Form");
        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegister(primaryStage));
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = Login.loginDB(username, password);

        if (user != null) {
            // Login successful, switch to the password dashboard.
            PasswordManagerDashboard dashboard = new PasswordManagerDashboard(user);
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