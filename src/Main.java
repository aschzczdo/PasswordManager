import database.DatabaseConnection;
import user.Login;
import user.UserDbConnection;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String username = "";
        String password = "";
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        System.out.println("What's your username?");
        username = sc.next();
        System.out.println("What's your password");
        password = sc.next();
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("\033[32m" + "Connection to the DB successfull!" + "\033[0m");
                if(login.authentication(username,password)){
                    System.out.println("Welcome " + username + " your password is: " + password);
                }else{
                    System.out.println("Invalid");
                }
            } else {
                System.out.println("Failed to establish a connection to the database.");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error occurred while connecting to the database:");
            e.printStackTrace();
        }
    }
}