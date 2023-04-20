package user;

import AES.Decrypt;
import AES.SecretKey;
import database.DatabaseConnection;
import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public static User loginDB(String username, String password) {
        UserDbConnection userDbConnection = new UserDbConnection();

        if (userDbConnection.authenticateUser(username, password)!=null) {
            String query = "SELECT * FROM USERS WHERE username=?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement statement = conn.prepareStatement(query)) {

                statement.setString(1, username);
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    System.out.println("\033[32m" + "Login successful: " + username + "\033[0m");

                    // Create and return the User object
                    int id = resultSet.getInt("user_id");
                    String encryptedPassword = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    String phoneNumber = resultSet.getString("phonenumber");
                    byte[] salt = resultSet.getBytes("salt");

                    User user = new User(id, username, encryptedPassword, email, phoneNumber, salt);
                    return user;
                }
            } catch (SQLException e) {
                System.out.println("Error logging in user.");
                e.printStackTrace();
                return null;
            }
        } else {
            System.out.println("Incorrect username or password.");
            return null;
        }
        return null;
    }
}

