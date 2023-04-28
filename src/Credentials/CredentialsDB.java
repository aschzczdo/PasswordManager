package Credentials;

import AES.Decrypt;
import AES.Encrypt;
import AES.SecretKey;
import AES.SecurePwdStorage;
import database.DatabaseConnection;
import ui.CredentialsUI;
import user.User;
import javax.crypto.spec.SecretKeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CredentialsDB {
    private String plainpwd;
    public CredentialsDB(){
    }
    public boolean addCredential(User user, int userId, String websiteUrl, String websiteName, String username, String email, String password) {
        String query = "INSERT INTO CREDENTIALS (user_id, websiteurl, websitename, username, email, password) VALUES (?, ?, ?, ?, ?, ?)";
        CredentialsUI credentialsUI = new CredentialsUI();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            System.out.println("CredentialsDB.addCredential: plainpwd = " + SecurePwdStorage.retrievePassword());
            // Encrypt the credentials before saving
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(SecurePwdStorage.retrievePassword(), user.getSalt());
            String encryptedUsername = Encrypt.encryptData(username, secretKeySpec);
            String encryptedEmail = Encrypt.encryptData(email, secretKeySpec);
            String encryptedPassword = Encrypt.encryptData(password, secretKeySpec);

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

    public boolean deleteCredential(User user, int credentialId) {
        String query = "DELETE FROM CREDENTIALS WHERE user_id = ? AND id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, user.getId());
            statement.setInt(2, credentialId);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Credentials> getAllCredentialsForUser(User user) {
        List<Credentials> credentialsList = new ArrayList<>();

        String query = "SELECT * FROM CREDENTIALS WHERE user_id = ?";
        SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(SecurePwdStorage.retrievePassword(), user.getSalt());

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user.getId());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int cred_id = resultSet.getInt("cred_id");
                    int user_id = resultSet.getInt("user_id");
                    String websiteurl = resultSet.getString("websiteurl");
                    String websitename = resultSet.getString("websitename");
                    String encryptedusername = resultSet.getString("username");
                    String encryptedemail = resultSet.getString("email");
                    String encryptedpassword = resultSet.getString("password");

                    // Decrypt the data using the created secretKey
                    String decryptedusername = Decrypt.decryptData(resultSet.getString("username"),secretKeySpec);
                    String decryptedemail = Decrypt.decryptData(encryptedemail,secretKeySpec);
                    String decryptedpassword = Decrypt.decryptData(encryptedpassword,secretKeySpec);

                    Credentials credentials = new Credentials(cred_id, user_id, websiteurl, websitename, decryptedusername, decryptedemail, decryptedpassword);
                    credentialsList.add(credentials);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return credentialsList;
    }


}
