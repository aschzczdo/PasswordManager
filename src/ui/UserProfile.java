package ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import user.User;

import java.io.File;

public class UserProfile extends VBox {
    private ImageView profileImageView;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField emailField;
    private TextField phoneNumberField;

    public UserProfile(User user) {
        setSpacing(10);
        setAlignment(Pos.TOP_CENTER);

        // Profile image
        profileImageView = new ImageView();
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        Circle clip = new Circle(50, 50, 50);
        profileImageView.setClip(clip);

        // Load the user's profile picture
        Image image = new Image("https://via.placeholder.com/100"); // Change to the actual image URL or file path
        profileImageView.setImage(image);

        // Change profile picture button
        Button changeProfilePictureButton = new Button("Change Profile Picture");
        changeProfilePictureButton.setOnAction(e -> handleChangeProfilePicture());

        // Username
        Text usernameLabel = new Text("Username:");
        usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        usernameField = new TextField(user.getUsername());
        usernameField.setDisable(true);

        // Password
        Text passwordLabel = new Text("Password:");
        passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        passwordField = new PasswordField();
        passwordField.setPromptText("New Password");

        // Email
        Text emailLabel = new Text("Email:");
        emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        emailField = new TextField(user.getEmail());
        emailField.setDisable(true);

        // Phone Number
        Text phoneNumberLabel = new Text("Phone Number:");
        phoneNumberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        phoneNumberField = new TextField(user.getPhoneNumber());
        phoneNumberField.setDisable(true);

        // Save changes button
        Button saveChangesButton = new Button("Save Changes");
        saveChangesButton.setOnAction(e -> handleSaveChanges(user));

        getChildren().addAll(profileImageView, changeProfilePictureButton, usernameLabel, usernameField, passwordLabel, passwordField, emailLabel, emailField, phoneNumberLabel, phoneNumberField, saveChangesButton);
    }

    private void handleChangeProfilePicture() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image newImage = new Image(selectedFile.toURI().toString());
            profileImageView.setImage(newImage);

            // Update the user's profile picture in the database
            // TODO: Implement the logic to save the image file and update the user's profile picture in the database
        }
    }

    private void handleSaveChanges(User user) {
        // Update the user's password, email, and phone number in the database
        // TODO: Implement the logic to update the user's password, email, and phone number in the database

        // Show a success message
        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Profile Updated");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Your profile has been updated successfully.");
        successAlert.showAndWait();
    }
}