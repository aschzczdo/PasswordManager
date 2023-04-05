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

public class Login {
    public static User loginDB(String username, String password) {
        String query = "SELECT * FROM USERS WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found.");
                return null;
            }

            String encryptedPassword = resultSet.getString("password");
            byte[] salt = resultSet.getBytes("salt");
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password, salt);
            String enteredPassword = Encrypt.encryptPassword(password, secretKeySpec);

            if (!encryptedPassword.equals(enteredPassword)) {
                System.out.println("Incorrect password.");
                return null;
            }

            System.out.println("\033[32m" + "Login successful: " + username + "\033[0m");

            // Create and return the User object
            int id = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String phoneNumber = resultSet.getString("phonenumber");

            User user = new User(id, username, encryptedPassword, email, phoneNumber, salt);
            return user;

        } catch (SQLException e) {
            System.out.println("Error logging in user.");
            e.printStackTrace();
            return null;
        }
    }
}
