package user;

import AES.Encrypt;
import AES.SecretKey;
import database.DatabaseConnection;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

//All function that requires user connection with DB
public class UserDbConnection {
    //Método para buscar a un usuario dado un username y una password en la BD.
    public static User findByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet resultset = statement.executeQuery()) {
                if (resultset.next()) {
                    user = new User();
                    user.setId(resultset.getInt("user_id"));
                    user.setUsername(resultset.getString("username"));
                    user.setPassword(resultset.getString("password"));
                    user.setEmail(resultset.getString("email"));
                    user.setPhoneNumber(resultset.getString("phonenumber"));
                    user.setSalt(resultset.getBytes("salt"));

                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        }
        return user;
    }
    public static User authenticateUser(String username, String password) {
        User user = findByUsername(username);

        if (user != null) {
            // Generate a SecretKeySpec object using the user's salt and the provided password
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password, user.getSalt());

            // Encrypt the provided password using the secretKeySpec
            String encryptedPassword = Encrypt.encryptData(password, secretKeySpec);

            // Compare the encrypted password with the one stored in the database
            if (user.getPassword().equals(encryptedPassword)) {
                return user; // Authentication successful
            }
        }

        return null; // Authentication failed
    }

    public boolean updatePassword(User user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your new password:");
        String newPassword = sc.next();
        System.out.println("Confirm your new password:");
        String confirmPassword = sc.next();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return false;
        }

        // Generate a new salt for the user
        byte[] newSalt = SecretKey.generateSalt();

        // Encrypt the new password using the new salt
        SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(newPassword, newSalt);
        String encryptedNewPassword = Encrypt.encryptData(newPassword, secretKeySpec);

        // Prepare the SQL query to update the password and salt in the database
        String query = "UPDATE USERS SET password=?, salt=? WHERE username=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            // Set the values for the prepared statement
            statement.setString(1, encryptedNewPassword);
            statement.setBytes(2, newSalt);
            statement.setString(3, user.getUsername());

            // Execute the update query
            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\033[32m" + "Password updated for user: " + user.getUsername() + "\033[0m");
                return true;
            } else {
                System.out.println("Error trying to update the password. Please try again.");
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating password", e);
        }
    }
}
