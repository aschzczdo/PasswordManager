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

public class Register {

    public static User registerForm() {
        String username, password, confirmpassword, email, confirmemail, phonenumber;
        byte[] salt;
        Scanner sc = new Scanner(System.in);
        System.out.println("Type your username:");
        username = sc.next();
        System.out.println("Type your password:");
        password = sc.next();
        System.out.println("Confirm your password:");
        confirmpassword = sc.next();
        System.out.println("Type your email: ");
        email = sc.next();
        System.out.println("Confirm your email");
        confirmemail = sc.next();
        System.out.println("Phone number:");
        phonenumber = sc.next();

        User user = new User();

        if (!password.equals(confirmpassword)) {
            System.out.println("Your password doesn't match");
            return null;
        } else if (!email.equals(confirmemail)) {
            System.out.println("Your email doesn't match");
            return null;
        } else {
            salt = SecretKey.generateSalt();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhoneNumber(phonenumber);
            user.setSalt(salt);
            return user;
        }
    }
    //Método para registrar usuarios en la BD
    public static boolean registerUserDB(User user) {
        String query = "SELECT * FROM USERS WHERE username=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("User already exists.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error checking for existing user.");
            e.printStackTrace();
            return false;
        }

        query = "INSERT INTO USERS (username, password, email, phonenumber, salt) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            SecretKeySpec secretKeySpec = SecretKey.createSecretKeySpec(user.getPassword(), user.getSalt());
            String encryptedPassword = Encrypt.encryptPassword(user.getPassword(), secretKeySpec);

            statement.setString(1, user.getUsername());
            statement.setString(2, encryptedPassword);
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setBytes(5, user.getSalt());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("\033[32m" + "User registered: " + user.getUsername() + "\033[0m");
                return true;
            } else {
                System.out.println("Error trying to register. Please try again.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error registering user.");
            e.printStackTrace();
            return false;
        }
    }
}