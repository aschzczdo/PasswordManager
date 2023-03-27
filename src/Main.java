import AES.Decrypt;
import AES.Encrypt;
import AES.SecretKey;
import database.DatabaseConnection;
import user.Login;
import user.Register;
import user.User;
import user.UserDbConnection;

import javax.crypto.spec.SecretKeySpec;
import java.sql.Connection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int opcion;
        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("\033[32m" + "Connection to the DB successfull!" + "\033[0m");
                System.out.println("Pres 1 to register");
                System.out.println("Press 2 to login");
                opcion = sc.nextInt();
                switch (opcion){
                    case 1:
                        User user = Register.registerForm();
                        boolean success = Register.registerUserDB(user);
                        break;
                    case 2:
                        user = Login.loginForm();
                        Login.loginDB(user);
                        break;

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