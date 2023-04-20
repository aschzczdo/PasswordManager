package ui;

import Credentials.CredentialsDB;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import user.User;

public class CredentialsFormUI {
    public static VBox credentialsForm(User user) {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        TextField websiteUrlField = new TextField();
        websiteUrlField.setPromptText("Website URL");
        TextField websiteNameField = new TextField();
        websiteNameField.setPromptText("Website Name");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
            // Get values from the form fields
            String websiteUrl = websiteUrlField.getText();
            String websiteName = websiteNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Call the addCredential method from the CredentialsDB class
            CredentialsDB credentialsDB = new CredentialsDB();
            boolean success = credentialsDB.addCredential(user, user.getId(), websiteUrl, websiteName, username, email, password);

            // Create an Alert to display the message
            Alert alert;
            if (success) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Added Credentials!");
                alert.setContentText("Credential added successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error trying to add credentials");
                alert.setContentText("Failed to add credential.");
            }
            alert.showAndWait();

            // Clear the form fields
            websiteUrlField.clear();
            websiteNameField.clear();
            usernameField.clear();
            emailField.clear();
            passwordField.clear();
        });


        form.getChildren().addAll(websiteUrlField, websiteNameField, usernameField, emailField, passwordField, addButton);

        return form;
    }

}