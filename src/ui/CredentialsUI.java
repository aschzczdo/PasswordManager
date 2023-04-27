package ui;

import Credentials.CredentialsDB;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import user.User;

public class CredentialsUI extends VBox {
    private User user;
    private String password;
    private CredentialsDB credentialsDB;

    public CredentialsUI(User user, String password) {
        this.user = user;
        this.password = password;
        this.credentialsDB = new CredentialsDB();

        setSpacing(10);
        setPadding(new Insets(15, 15, 15, 15));

        createMenu();
    }

    private Node createMenu() {
        Button addCredentialsButton = new Button("Add Credentials");
        addCredentialsButton.setOnAction(e -> {
            getChildren().clear();
            getChildren().add(new CredentialsUI(user, password).credentialsForm(user));
        });

        Button editCredentialsButton = new Button("Edit Credentials");
        editCredentialsButton.setOnAction(e -> {
            // TODO: Implement the logic for editing credentials
            System.out.println("Edit Credentials button clicked");
        });

        Button deleteCredentialsButton = new Button("Delete Credentials");
        deleteCredentialsButton.setOnAction(e -> {
            // TODO: Implement the logic for deleting credentials
            System.out.println("Delete Credentials button clicked");
        });

        getChildren().addAll(addCredentialsButton, editCredentialsButton, deleteCredentialsButton);
        return this;
    }

    public Node credentialsForm(User user) {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label websiteUrlLabel = new Label("Website URL:");
        TextField websiteUrlField = new TextField();
        websiteUrlField.setPromptText("Website URL");
        gridPane.add(websiteUrlLabel, 0, 0);
        gridPane.add(websiteUrlField, 1, 0);

        Label websiteNameLabel = new Label("Website Name:");
        TextField websiteNameField = new TextField();
        websiteNameField.setPromptText("Website Name");
        gridPane.add(websiteNameLabel, 0, 1);
        gridPane.add(websiteNameField, 1, 1);

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        gridPane.add(usernameLabel, 0, 2);
        gridPane.add(usernameField, 1, 2);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailField, 1, 3);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        gridPane.add(passwordLabel, 0, 4);
        gridPane.add(passwordField, 1, 4);

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

            // Show the credentials list
            getChildren().clear();
        });

        form.getChildren().addAll(gridPane, addButton);

        return form;
    }
}
