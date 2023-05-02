package user;

import AES.Encrypt;
import AES.SecretKey;
import AES.SecurePwdStorage;
import database.DatabaseConnection;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        SecurePwdStorage securePwdStorage = new SecurePwdStorage();
        if (user != null) {
            // Generate a SecretKeySpec object using the user's salt and the provided password
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password, user.getSalt());

            // Encrypt the provided password using the secretKeySpec
            String encryptedPassword = Encrypt.encryptData(password, secretKeySpec);

            // Compare the encrypted password with the one stored in the database
            if (user.getPassword().equals(encryptedPassword)) {
                securePwdStorage.storePassword(password);
                return user; // Authentication successful
            }
        }

        return null; // Authentication failed
    }

    public boolean updatePassword(User user, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return false;
        }

        // Generate a new salt for the user
        byte[] newSalt = SecretKey.generateSalt();
        SecurePwdStorage.storePassword(newPassword);
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
    //Method to update email
    public boolean updateEmail(User user, String newEmail) {
        String query = "UPDATE USERS SET email=? WHERE username=?";

        // Generate the SecretKeySpec object using the user's password and salt
        SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(user.getPassword(), user.getSalt());

        // Encrypt the new email using the SecretKeySpec object
        String encryptedEmail = Encrypt.encryptData(newEmail, secretKeySpec);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, encryptedEmail);
            statement.setString(2, user.getUsername());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error updating email", e);
        }
    }

    public boolean updatePhoneNumber(User user, String newPhoneNumber) {
        String query = "UPDATE USERS SET phonenumber=? WHERE username=?";

        // Generate the SecretKeySpec object using the user's password and salt
        SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(user.getPassword(), user.getSalt());

        // Encrypt the new phone number using the SecretKeySpec object
        String encryptedPhoneNumber = Encrypt.encryptData(newPhoneNumber, secretKeySpec);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, encryptedPhoneNumber);
            statement.setString(2, user.getUsername());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.err.println("Error updating phone number: " + e.getMessage());
            return false;

        }
    }

}
