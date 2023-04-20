package ui;

import AES.Decrypt;
import AES.Encrypt;
import AES.SecretKey;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import user.User;
import user.UserDbConnection;

import javax.crypto.spec.SecretKeySpec;

public class UserProfileUI {
    public static VBox userProfile(User user) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));

        Label nameLabel = new Label("Username:");
        TextField nameField = new TextField(user.getUsername());
        nameField.setDisable(true);
        Label emailLabel = new Label("Email:");
       // SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(user.getPassword(), user.getSalt());
        String encryptedEmail = user.getEmail();
        //String decryptedEmail = Decrypt.decryptData(encryptedEmail, secretKeySpec);
        //TextField emailField = new TextField(decryptedEmail);

        Label phoneNumberLabel = new Label("Phone Number:");
        //String decryptedPhoneNumber = Decrypt.decryptData(user.getPhoneNumber(), secretKeySpec);
        //TextField phoneNumberField = new TextField(decryptedPhoneNumber);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Current password");

        Label newPasswordLabel = new Label("New Password:");
        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New password");
        newPasswordField.setDisable(true);

        Label confirmPasswordLabel = new Label("Confirm New Password:");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm new password");
        confirmPasswordField.setDisable(true);

        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(emailLabel, 0, 1);
        //gridPane.add(emailField, 1, 1);
        gridPane.add(phoneNumberLabel, 0, 2);
        //gridPane.add(phoneNumberField, 1, 2);
        gridPane.add(passwordLabel, 0, 3);
        gridPane.add(passwordField, 1, 3);
        gridPane.add(newPasswordLabel, 0, 4);
        gridPane.add(newPasswordField, 1, 4);
        gridPane.add(confirmPasswordLabel, 0, 5);
        gridPane.add(confirmPasswordField, 1, 5);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
           // emailField.setDisable(false);
           //phoneNumberField.setDisable(false);
            passwordField.setDisable(false);
            newPasswordField.setDisable(false);
            confirmPasswordField.setDisable(false);
        });

        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            //TODO: Implement Save Button Logic
            });

            HBox buttonBox = new HBox(10);
            buttonBox.getChildren().addAll(editButton, saveButton);

            VBox userProfile = new VBox(10);
            userProfile.getChildren().addAll(gridPane, buttonBox);

            return userProfile;
        }


    }

