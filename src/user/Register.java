package user;

import AES.Encrypt;
import AES.SecretKey;
import database.DatabaseConnection;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {

    public static boolean registerForm(String username, String password, String confirmPassword,
                                         String email, String confirmEmail, String phoneNumber) {
        if (!password.equals(confirmPassword)) {
            System.out.println("Your password doesn't match");
            return false;
        } else if (!email.equals(confirmEmail)) {
            System.out.println("Your email doesn't match");
            return false;
        }
        return true;
    }

    public static boolean registerUserDB(String username, String password, String email,
                                         String phoneNumber, byte[] salt) {
        String query = "SELECT * FROM USERS WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("User already exists.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking for existing user.");
            e.printStackTrace();
            return false;
        }

        query = "INSERT INTO USERS (username, password, email, phonenumber, salt) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password, salt);
            String encryptedPassword = Encrypt.encryptPassword(password, secretKeySpec);

            statement.setString(1, username);
            statement.setString(2, encryptedPassword);
            statement.setString(3, email);
            statement.setString(4, phoneNumber);
            statement.setBytes(5, salt);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\033[32m" + "User registered: " + username + "\033[0m");
                return true;
            } else {
                System.out.println("Error trying to register. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error registering user.");
            e.printStackTrace();
            return false;
        }
    }
}
