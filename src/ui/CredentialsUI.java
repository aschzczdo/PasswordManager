package ui;

import Credentials.Credentials;
import Credentials.CredentialsDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import user.User;
import user.UserDbConnection;

import java.util.List;
import java.util.Optional;

public class CredentialsUI extends VBox {
    private User user;
    private String password;
    private CredentialsDB credentialsDB;
    private String plainpwd;

    public CredentialsUI() {

    }

    public CredentialsUI(User user, String password) {
        this.user = user;
        this.password = password;
        this.credentialsDB = new CredentialsDB();
        setSpacing(10);
        setPadding(new Insets(15, 15, 15, 15));

        TabPane tabPane = new TabPane();

        Tab addCredentialsTab = new Tab("Add Credentials");
        addCredentialsTab.setContent(createAddCredentialsForm());
        addCredentialsTab.setClosable(false);

        Tab viewCredentialsTab = new Tab("View Credentials");
        viewCredentialsTab.setContent(new Label("Click on the tab to view credentials."));
        viewCredentialsTab.setClosable(false);

        Tab editCredentialsTab = new Tab("Edit Credentials");
        editCredentialsTab.setContent(new Label("Click on the tab to edit credentials."));
        editCredentialsTab.setClosable(false);

        tabPane.getTabs().addAll(addCredentialsTab, viewCredentialsTab, editCredentialsTab);

        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab == viewCredentialsTab || newTab == editCredentialsTab) {
                // Prompt for the user's password
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Password Verification");
                dialog.setHeaderText("Please enter your password:");

                // Create password field
                PasswordField passwordField = new PasswordField();
                dialog.getDialogPane().setContent(passwordField);

                // Add OK and Cancel buttons
                ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

                // Set the OK button as the default
                Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
                okButton.requestFocus();

                // Set the result converter
                dialog.setResultConverter(buttonType -> {
                    if (buttonType == okButtonType) {
                        return passwordField.getText();
                    }
                    return null;
                });

                // Show the dialog and wait for the result
                Optional<String> result = dialog.showAndWait();
                // Check the entered password
                result.ifPresent(enteredPassword -> {
                    if (UserDbConnection.authenticateUser(user.getUsername(), enteredPassword) != null) {
                        plainpwd = enteredPassword;
                        if (newTab == viewCredentialsTab) {
                            viewCredentialsTab.setContent(createViewCredentials());
                        } else {
                            editCredentialsTab.setContent(createEditCredentials());
                        }
                    } else {
                        // Incorrect password
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Authentication Failed");
                        alert.setHeaderText("Invalid Password");
                        alert.setContentText("The entered password is incorrect. Please try again.");
                        alert.showAndWait();

                        // Reset the selected tab to the previous one
                        tabPane.getSelectionModel().select(oldTab);
                    }
                });

            }
        });

        getChildren().add(tabPane);

    }

    private Node createAddCredentialsForm() {
        return credentialsForm(user, null);
    }

    private Node createViewCredentials() {
        TableView<Credentials> tableView = new TableView<>();
        tableView.setItems(loadCredentials());

        TableColumn<Credentials, String> websiteUrlColumn = new TableColumn<>("Website URL");
        websiteUrlColumn.setCellValueFactory(new PropertyValueFactory<>("websiteUrl"));
        TableColumn<Credentials, String> websiteNameColumn = new TableColumn<>("Website Name");
        websiteNameColumn.setCellValueFactory(new PropertyValueFactory<>("websiteName"));
        TableColumn<Credentials, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Credentials, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Credentials, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableView.getColumns().addAll(websiteUrlColumn, websiteNameColumn, usernameColumn, emailColumn, passwordColumn);

        return tableView;
    }

    private Node createEditCredentials() {
        TableView<Credentials> tableView = new TableView<>();
        tableView.setItems(loadCredentials());

        TableColumn<Credentials, String> websiteUrlColumn = new TableColumn<>("Website URL");
        websiteUrlColumn.setCellValueFactory(new PropertyValueFactory<>("websiteUrl"));
        TableColumn<Credentials, String> websiteNameColumn = new TableColumn<>("Website Name");
        websiteNameColumn.setCellValueFactory(new PropertyValueFactory<>("websiteName"));
        TableColumn<Credentials, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        TableColumn<Credentials, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        TableColumn<Credentials, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        tableView.getColumns().addAll(websiteUrlColumn, websiteNameColumn, usernameColumn, emailColumn, passwordColumn);

        Button editButton = new Button("Edit Selected Credential");
        editButton.setOnAction(e -> {
            // Get the selected credential
            Credentials selectedCredential = tableView.getSelectionModel().getSelectedItem();

            if (selectedCredential != null) {
                // Display the edit form
                Node editForm = credentialsForm(user, selectedCredential);
                getChildren().clear();
                getChildren().add(editForm);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Credential Selected");
                alert.setContentText("Please select a credential to edit.");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(tableView, editButton);
        return layout;
    }

    public Node credentialsForm(User user, Credentials credentials) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label websiteUrlLabel = new Label("Website URL:");
        gridPane.add(websiteUrlLabel, 0, 0);
        TextField websiteUrlField = new TextField();
        gridPane.add(websiteUrlField, 1, 0);

        Label websiteNameLabel = new Label("Website Name:");
        gridPane.add(websiteNameLabel, 0, 1);
        TextField websiteNameField = new TextField();
        gridPane.add(websiteNameField, 1, 1);

        Label usernameLabel = new Label("Username:");
        gridPane.add(usernameLabel, 0, 2);
        TextField usernameField = new TextField();
        gridPane.add(usernameField, 1, 2);

        Label emailLabel = new Label("Email:");
        gridPane.add(emailLabel, 0, 3);
        TextField emailField = new TextField();
        gridPane.add(emailField, 1, 3);

        Label passwordLabel = new Label("Password:");
        gridPane.add(passwordLabel, 0, 4);
        PasswordField passwordField = new PasswordField();
        gridPane.add(passwordField, 1, 4);

        if (credentials != null) {
            websiteUrlField.setText(credentials.getWebsiteUrl());
            websiteNameField.setText(credentials.getWebsiteName());
            usernameField.setText(credentials.getUsername());
            emailField.setText(credentials.getEmail());
            passwordField.setText(credentials.getPassword());
        }

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
            // Pass the plainPassword to the addCredential method
            boolean success = credentialsDB.addCredential(user, user.getId(), websiteUrl, websiteName, username, email, password);

            // Refresh the list of credentials
            // TODO: Implement the logic to refresh the list of credentials
        });


        VBox formLayout = new VBox(10);
        formLayout.getChildren().addAll(gridPane, addButton);
        return formLayout;
    }

    private ObservableList<Credentials> loadCredentials() {
        // Retrieve the list of Credentials from the database using the CredentialsDB class
        List<Credentials> credentialsList = credentialsDB.getAllCredentialsForUser(user);

        // Convert the List<Credentials> to an ObservableList<Credentials>
        ObservableList<Credentials> credentialsObservableList = FXCollections.observableArrayList(credentialsList);

        return credentialsObservableList;
    }
}