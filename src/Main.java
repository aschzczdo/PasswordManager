import database.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Connection to the database successful!");
                connection.close();
            } else {
                System.out.println("Failed to establish a connection to the database.");
            }
        } catch (Exception e) {
            System.out.println("Error occurred while connecting to the database:");
            e.printStackTrace();
        }
    }
}