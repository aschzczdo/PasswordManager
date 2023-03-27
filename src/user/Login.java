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

        public static User loginForm() {
            Scanner sc = new Scanner(System.in);
            String username, password;

            System.out.println("Type your username:");
            username = sc.next();
            System.out.println("Type your password:");
            password = sc.next();

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            return user;
        }

    public static boolean loginDB(User user) {
        String query = "SELECT * FROM USERS WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                System.out.println("User not found.");
                return false;
            }

            String encryptedPassword = resultSet.getString("password");
            byte[] salt = resultSet.getBytes("salt");
            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(user.getPassword(), salt);
            String enteredPassword = Encrypt.encryptPassword(user.getPassword(), secretKeySpec);

            if (!encryptedPassword.equals(enteredPassword)) {
                System.out.println("Incorrect password.");
                return false;
            }

            System.out.println("\033[32m" + "Login successful: " + user.getUsername() + "\033[0m");
            return true;

        } catch (SQLException e) {
            System.out.println("Error logging in user.");
            e.printStackTrace();
            return false;
        }
    }
}