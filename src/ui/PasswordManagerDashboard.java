package ui;

import AES.SecurePwdStorage;
import Credentials.Credentials;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import user.User;
import user.UserDbConnection;

public class PasswordManagerDashboard extends Application {
    private User user;
    private String password;
    private Tab notesTab;
    private CredentialsUI credentialsUI;
    private Tab credentialsTab;
    private TabPane tabPane;

    public PasswordManagerDashboard(User user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("dashboard-background");

        // Logout button
        Button logoutButton = new Button("Logout");
        logoutButton.getStyleClass().add("logout-button");
        logoutButton.setOnAction(e -> handleLogout(primaryStage));
        root.setBottom(logoutButton);
        BorderPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(logoutButton, new Insets(0, 10, 10, 0));

        tabPane = new TabPane();
        tabPane.setTabMinWidth(150);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        //Create user profile tab
        Tab userProfileTab = new Tab("User Profile");
        userProfileTab.setContent(createUserProfileTab());
        userProfileTab.setClosable(false);
        //Create credentials tab
        Tab credentialsTab = new Tab("Credentials");
        credentialsTab.setContent(createCredentialsTab());
        credentialsTab.setClosable(false);
        //Create notes tab
        notesTab = new Tab("Notes");
        notesTab.setContent(createNotesTab(null));
        notesTab.setClosable(false);
        //Create contact tab
        Tab contactTab = new Tab("Contact");
        contactTab.setContent(createContactTab());
        contactTab.setClosable(false);

        tabPane.getTabs().addAll(userProfileTab, credentialsTab, notesTab, contactTab);

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
        UserProfileUI userProfileUI = new UserProfileUI(loggedinUser);
        return userProfileUI;
    }

    public VBox createCredentialsTab() {
        User loggedinUser = UserDbConnection.findByUsername(user.getUsername());
        credentialsUI = new CredentialsUI(loggedinUser, this);
        return credentialsUI;
    }
    VBox createNotesTab(Credentials selectedCredential) {
        if (selectedCredential == null) {
            return new VBox(createNotesView());
        }
        NotesUI notesUI = new NotesUI(user, selectedCredential, this);
        return notesUI;
    }
    private TableView<Credentials> createNotesView() {
        TableView<Credentials> tableView = credentialsUI.createCredentialsTableView();
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                VBox notesUI = createNotesTab(newSelection);
                notesTab.setContent(notesUI);
            }
        });
        return tableView;
    }
    private VBox createContactTab() {
        VBox contactContent = new VBox();
        // TODO: Add content for the Contact tab
        return contactContent;
    }

    public Tab getNotesTab() {

        return notesTab;
    }

    public Tab getCredentialsTab(){

    return credentialsTab;
    }

    public TabPane getTabPane() {

        return this.tabPane;
    }

    public void showNotesForCredential(Credentials selectedCredential) {
        VBox notesUI = createNotesTab(selectedCredential);
        notesTab.setContent(notesUI);
    }

    public static void main(String[] args) {

        launch(args);
    }

}