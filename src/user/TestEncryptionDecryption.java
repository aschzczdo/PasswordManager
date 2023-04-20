package user;

import AES.Decrypt;
import AES.Encrypt;
import AES.SecretKey;
import database.DatabaseConnection;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestEncryptionDecryption {
    public static void main(String[] args) {
        UserDbConnection userDbConnection = new UserDbConnection();

        // You can change this to the desired username for testing
        String username = "yasufake1";

        // Retrieve the user by username from the database
        User user = userDbConnection.findByUsername(username);

        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        // Password used for testing
        String password = "test123";

        // Use the user's salt and provided password to generate the secret key
        byte[] salt = user.getSalt();
        SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(password, salt);

        // Email is already encrypted on the db
        System.out.println("Encrypted email: " + user.getEmail());

        // Decrypt the encrypted email
        String decryptedEmail = Decrypt.decryptData(user.getEmail(), secretKeySpec);
        System.out.println("Decrypted email: " + decryptedEmail);

        if (user.getEmail().equals(decryptedEmail)) {
            System.out.println("Encryption and decryption process is working correctly.");
        } else {
            System.out.println("There's an issue with the encryption and decryption process.");
        }
    }
}
