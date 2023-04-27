package ui;

import AES.Decrypt;
import AES.SecretKey;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import user.User;
import user.UserDbConnection;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.net.URL;
import java.util.HashMap;

public class UserProfileUI extends VBox {
    private User loggedInUser;
    private String plainpassword;

    public UserProfileUI(User loggedInUser, String plainpassword) {
        this.loggedInUser = loggedInUser;
        this.plainpassword = plainpassword;
        // Load the CSS file
        URL cssURL = getClass().getResource("/resources/styles.css");
        if (cssURL != null) {
            getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.out.println("Could not load CSS file.");
        }

        initComponents();
    }

    private void initComponents() {
        GridPane grid = new GridPane();
        grid.getStyleClass().add("user-profile-grid");
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Label lblUsername = new Label("Username:");
        TextField txtUsername = new TextField(loggedInUser.getUsername());
        txtUsername.setEditable(false);

        // Decrypt email and phone number
        String decryptedEmail = "";
        String decryptedPhoneNumber = "";
        try {
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(plainpassword, loggedInUser.getSalt());
            decryptedEmail = Decrypt.decryptData(loggedInUser.getEmail(), secretKeySpec);
            decryptedPhoneNumber = Decrypt.decryptData(loggedInUser.getPhoneNumber(), secretKeySpec);
        } catch (Exception e) {
            System.out.println("Error decrypting email and phone number: " + e.getMessage());
        }

        Label lblPhoneNumber = new Label("Phone number:");
        TextField txtPhoneNumber = new TextField(decryptedPhoneNumber);
        txtPhoneNumber.setEditable(false);
        Button btnEditPhoneNumber = new Button("Edit");

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField(decryptedEmail);
        txtEmail.setEditable(false);
        Button btnEditEmail = new Button("Edit");

        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setEditable(false);
        Button btnEditPassword = new Button("Edit");

        Label lblCurrentPassword = new Label("Current Password:");
        PasswordField txtCurrentPassword = new PasswordField();
        Label lblNewPassword = new Label("New Password:");
        PasswordField txtNewPassword = new PasswordField();
        Label lblConfirmPassword = new Label("Confirm Password:");
        PasswordField txtConfirmPassword = new PasswordField();
        Button btnUpdatePassword = new Button("Update Password");

        grid.add(lblUsername, 0, 0);
        grid.add(txtUsername, 1, 0);

        grid.add(lblPhoneNumber, 0, 1);
        grid.add(txtPhoneNumber, 1, 1);
        grid.add(btnEditPhoneNumber, 2, 1);

        grid.add(lblEmail, 0, 2);
        grid.add(txtEmail, 1, 2);
        grid.add(btnEditEmail, 2, 2);

        grid.add(lblPassword, 0, 3);
        grid.add(txtPassword, 1, 3);
        grid.add(btnEditPassword, 2, 3);

        getChildren().add(grid);

        boolean[] passwordFieldsVisible = {false};

        btnEditPassword.setOnAction(e -> {
            if (!passwordFieldsVisible[0]) {
                grid.add(lblCurrentPassword, 0, 4);
                grid.add(txtCurrentPassword, 1, 4);
                grid.add(lblNewPassword, 0, 5);
                grid.add(txtNewPassword, 1, 5);
                grid.add(lblConfirmPassword, 0, 6);
                grid.add(txtConfirmPassword, 1, 6);
                grid.add(btnUpdatePassword, 1, 7);
                passwordFieldsVisible[0] = true;
            } else {
                grid.getChildren().removeAll(lblCurrentPassword, txtCurrentPassword, lblNewPassword, txtNewPassword, lblConfirmPassword, txtConfirmPassword, btnUpdatePassword);
                passwordFieldsVisible[0] = false;
            }
        });

        // Inside UserProfileUI class

// Add a UserDbConnection instance as a class member
        UserDbConnection dbConnection = new UserDbConnection();

// Update the btnEditPhoneNumber, btnEditEmail, and btnUpdatePassword event handlers
        btnEditPhoneNumber.setOnAction(e -> {
            if (txtPhoneNumber.isEditable()) {
                txtPhoneNumber.setEditable(false);
                btnEditPhoneNumber.setText("Edit");

                if (dbConnection.updatePhoneNumber(loggedInUser, txtPhoneNumber.getText())) {
                    loggedInUser.setPhoneNumber(txtPhoneNumber.getText());
                } else {
                    System.out.println("Failed to update phone number.");
                }
            } else {
                txtPhoneNumber.setEditable(true);
                btnEditPhoneNumber.setText("Save");
            }
        });

        btnEditEmail.setOnAction(e -> {
            if (txtEmail.isEditable()) {
                txtEmail.setEditable(false);
                btnEditEmail.setText("Edit");

                if (dbConnection.updateEmail(loggedInUser, txtEmail.getText())) {
                    loggedInUser.setEmail(txtEmail.getText());
                } else {
                    System.out.println("Failed to update email.");
                }
            } else {
                txtEmail.setEditable(true);
                btnEditEmail.setText("Save");
            }
        });

        // btnUpdatePassword event handler
        btnUpdatePassword.setOnAction(e -> {
            String currentPassword = txtCurrentPassword.getText();
            String newPassword = txtNewPassword.getText();
            String confirmPassword = txtConfirmPassword.getText();

            if (dbConnection.updatePassword(loggedInUser, newPassword, confirmPassword)) {
                // Hide the password fields
                txtCurrentPassword.setVisible(false);
                txtNewPassword.setVisible(false);
                txtConfirmPassword.setVisible(false);
                btnUpdatePassword.setVisible(false);

                txtCurrentPassword.clear();
                txtNewPassword.clear();
                txtConfirmPassword.clear();
            } else {
                System.out.println("Failed to update password.");
            }
        });

    }
}