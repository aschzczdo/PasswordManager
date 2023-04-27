package ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import user.UserDbConnection;

public class PasswordManagerDashboard extends Application {
    private User user;
    private String password;
    public PasswordManagerDashboard(User user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> handleLogout(primaryStage));
        root.setBottom(logoutButton);
        BorderPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);

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
    private void handleLogout(Stage primaryStage) {
        try {
            LoginUI loginUI = new LoginUI();
            loginUI.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createUserProfileTab() {
        User loggedinUser = UserDbConnection.findByUsername(user.getUsername());
        UserProfileUI userProfileUI = new UserProfileUI(loggedinUser, password);
        return userProfileUI;
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