package user;


import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    public boolean authentication(String username, String password) {
        String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet resultset = statement.executeQuery()) {
                if (resultset.next()) {
                    System.out.println("\033[32m" + "Logged in: " + username + "\033[0m");
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username and password", e);
        }
        return false;
    }

}
