package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import ui.CredentialsFormUI;
public class PasswordManagerDashboard {

    private User user;
    private static BorderPane dashboardPane;

    public PasswordManagerDashboard(User user) {
        this.user = user;
    }
    public void start(Stage primaryStage) {
        dashboardPane = new BorderPane(); // Initialize the dashboardPane here
        dashboardPane.setLeft(createMenu());

        Scene scene = new Scene(dashboardPane, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/resources/styles.css").toExternalForm());
        primaryStage.setTitle("Password Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createMenu() {
        VBox menu = new VBox(10);
        menu.setPadding(new Insets(10, 10, 10, 10));
        menu.setStyle("-fx-background-color: #EDEDED;");

        Button userProfileButton = new Button("User Profile");
        userProfileButton.setOnAction(e -> handleUserProfile());
        Button credentialsButton = new Button("Credentials");
        credentialsButton.setOnAction(e -> handleCredentials());
        Button contactSupportButton = new Button("Contact Support");
        contactSupportButton.setOnAction(e -> handleContactSupport());

        menu.getChildren().addAll(userProfileButton, credentialsButton, contactSupportButton);

        return menu;
    }

    private void handleUserProfile() {
        // Implement the user profile view
    }

    private void handleCredentials() {
        TabPane credentialsTabPane = createCredentialsTabPane();
        dashboardPane.setCenter(credentialsTabPane);
    }

    private TabPane createCredentialsTabPane() {
        TabPane tabPane = new TabPane();

        Tab addCredentialsTab = new Tab("Add Credentials");
        CredentialsFormUI formUI = new CredentialsFormUI();
        addCredentialsTab.setContent(formUI.credentialsForm(user)); // Pass the user object here
        addCredentialsTab.setClosable(false);

        tabPane.getTabs().add(addCredentialsTab);

        // Add more tabs as needed

        return tabPane;
    }




    private void handleContactSupport() {
        // Implement the contact support view
    }

    public static Scene createDashboardScene(User user) {
        PasswordManagerDashboard dashboard = new PasswordManagerDashboard(user);
        VBox menu = dashboard.createMenu();
        dashboardPane = new BorderPane();
        dashboardPane.setLeft(menu);
        Scene dashboardScene = new Scene(dashboardPane, 800, 600);
        dashboardScene.getStylesheets().add(dashboard.getClass().getResource("/resources/styles.css").toExternalForm());
        return dashboardScene;
    }
}