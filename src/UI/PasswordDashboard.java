package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PasswordDashboard extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Top menu
        MenuBar menuBar = new MenuBar();
        Menu userProfileMenu = new Menu("User Profile");
        Menu credentialsMenu = new Menu("Credentials");
        Menu contactSupportMenu = new Menu("Contact Support");
        menuBar.getMenus().addAll(userProfileMenu, credentialsMenu, contactSupportMenu);
        root.setTop(menuBar);

        // Main content
        VBox mainContent = new VBox();
        // TODO: Add content to the mainContent VBox
        root.setCenter(mainContent);

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Password Manager Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Method to create the User Profile UI
    private VBox createUserProfile() {
        VBox userProfileContent = new VBox();
        // TODO: Implement User Profile UI
        return userProfileContent;
    }

    // Method to create the Credentials UI
    private VBox createCredentials() {
        VBox credentialsContent = new VBox();
        // TODO: Implement Credentials UI
        return credentialsContent;
    }

    // Method to create the Contact Support UI
    private VBox createContactSupport() {
        VBox contactSupportContent = new VBox();
        // TODO: Implement Contact Support UI
        return contactSupportContent;
    }
}
