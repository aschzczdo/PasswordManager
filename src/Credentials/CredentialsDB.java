package Credentials;

import AES.Encrypt;
import AES.SecretKey;
import database.DatabaseConnection;
import user.Login;
import user.User;

import javax.crypto.spec.SecretKeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CredentialsDB {
    public CredentialsDB(){
    }
    public boolean addCredential(User user, int userId, String websiteUrl, String websiteName, String username, String email, String password) {
        String query = "INSERT INTO CREDENTIALS (user_id, websiteurl, websitename, username, email, password) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Encrypt the credentials before saving
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(user.getPassword(), user.getSalt());
            String encryptedUsername = Encrypt.encryptPassword(username, secretKeySpec);
            String encryptedEmail = Encrypt.encryptPassword(email, secretKeySpec);
            String encryptedPassword = Encrypt.encryptPassword(password, secretKeySpec);

            statement.setInt(1, userId);
            statement.setString(2, websiteUrl);
            statement.setString(3, websiteName);
            statement.setString(4, encryptedUsername);
            statement.setString(5, encryptedEmail);
            statement.setString(6, encryptedPassword);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Add methods for updating, deleting, and retrieving credentials here
}
