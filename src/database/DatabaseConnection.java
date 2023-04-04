package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = System.getenv("PASSWORD_MANAGER_DB_URL");
    private static final String DB_USER = System.getenv("PASSWORD_MANAGER_DB_USER");
    private static final String DB_PASSWORD = System.getenv("PASSWORD_MANAGER_DB_PASSWORD");

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}