package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import user.UserDbConnection;

public class PasswordManagerDashboard extends Application {
    private User user;

    public PasswordManagerDashboard(User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        TabPane tabPane = new TabPane();

        Tab userProfileTab = new Tab("User Profile");
        userProfileTab.setContent(createUserProfileTab());
        userProfileTab.setClosable(false);

        Tab credentialsTab = new Tab("Credentials");
        credentialsTab.setContent(createCredentialsTab());
        credentialsTab.setClosable(false);

        Tab contactTab = new Tab("Contact");
        contactTab.setContent(createContactTab());
        contactTab.setClosable(false);

        tabPane.getTabs().addAll(userProfileTab, credentialsTab, contactTab);

        root.setCenter(tabPane);

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("resources/styles.css");

        primaryStage.setTitle("Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createUserProfileTab() {
        String username = user.getUsername();
        VBox userProfileContent = new VBox();
        User user = UserDbConnection.findByUsername(username); // Replace this with a method to get the current user
        // Create the user profile content
        VBox profileBox = UserProfileUI.userProfile(user);

        userProfileContent.getChildren().add(profileBox);
        return userProfileContent;
    }

    private VBox createCredentialsTab() {
        VBox credentialsContent = new VBox();
        // TODO: Add content for the Credentials tab
        return credentialsContent;
    }

    private VBox createContactTab() {
        VBox contactContent = new VBox();
        // TODO: Add content for the Contact tab
        return contactContent;
    }

    public static void main(String[] args) {
        launch(args);
    }
}