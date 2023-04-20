package ui;

import AES.SecretKey;
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
import user.Register;

public class RegisterUI extends Application {

    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextField confirmEmailField;
    private PasswordField confirmPasswordField;
    private TextField phoneNumberField;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        usernameField = new TextField();
        passwordField = new PasswordField();
        confirmPasswordField = new PasswordField();
        emailField = new TextField();
        confirmEmailField = new TextField();
        phoneNumberField = new TextField();
        statusLabel = new Label();

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameField, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(new Label("Confirm Password:"), 0, 2);
        gridPane.add(confirmPasswordField, 1, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(new Label("Confirm Email:"), 0, 4);
        gridPane.add(confirmEmailField, 1, 4);
        gridPane.add(new Label("Phone Number:"), 0, 5);
        gridPane.add(phoneNumberField, 1, 5);

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

        Scene scene = new Scene(vBox, 350, 400);
        scene.getStylesheets().add("resources/styles.css");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Register Form");
        primaryStage.setScene(scene);
        primaryStage.show();

        loginButton.setOnAction(e -> handleLogin());
        registerButton.setOnAction(e -> handleRegister());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (Login.loginDB(username, password) != null) {
            // Login successful, switch to the password dashboard.
            // TODO: Implement the password dashboard.
        } else {
            statusLabel.setText("Login failed. Please check your credentials.");
        }
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();
        String confirmEmail = confirmEmailField.getText();
        String phoneNumber = phoneNumberField.getText();

        if (Register.registerForm(username, password, confirmPassword, email, confirmEmail, phoneNumber)) {
            byte[] salt = SecretKey.generateSalt();
            if (Register.registerUserDB(username, password, email, phoneNumber, salt) != null) {
                statusLabel.setText("User registered successfully.");
                // Optionally, you can redirect the user to the password dashboard here.
                // TODO: Implement the password dashboard.
            } else {
                statusLabel.setText("Registration failed. Please try again.");
            }
        } else {
            statusLabel.setText("Invalid input. Please check your information and try again.");
        }
    }
}
