package user;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

//Clase para realizar operaciones en la base de datos relacionada con la gestión de usuarios.
public class UserDbConnection {
    //Método para registrar usuarios en la BD
    public boolean registerUserDB(User user) { //Recoge un usuario por parámetro
        String query = "INSERT INTO USERS (username, password, email, phonenumber) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            //no añadimos user_id porque se genera automaticamente en la BD.
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("\033[32m" + "User registered: " + user.getUsername() + "\033[0m");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
        return false;
    }
    //Método para añadir usuarios a nuestro programa, posteriormente enviados a la BD con el metodo registerUserDB
    public boolean registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the following details to register:");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        // Create a new User object with the provided information
        User newUser = new User(username, password, email, phoneNumber);

        // Call the register method to insert the new user into the database
        boolean registrationSuccessful = registerUserDB(newUser);

        if (registrationSuccessful) {
            System.out.println("Registration successful! You can now log in with your new account.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }

        return registrationSuccessful;
    }
    }


