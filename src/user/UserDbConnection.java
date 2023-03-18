package user;

import database.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

//Clase para realizar operaciones en la base de datos relacionada con la gestión de usuarios.
public class UserDbConnection {
    //Método para buscar a un usuario dado un username y una password en la BD.
    public User findByUsername(String username) {
        String query = "SELECT * FROM USERS WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet resultset = statement.executeQuery()) {
                if (resultset.next()) {
                    user = new User();
                    user.setId(resultset.getInt("user_id"));
                    user.setUsername(resultset.getString("username"));
                    user.setPassword(resultset.getString("password"));
                    user.setEmail(resultset.getString("email"));
                    user.setPhoneNumber(resultset.getString("phonenumber"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username", e);
        }
        return user;
    }

    //Método para buscar en la BD dado un usuario y una contraseña
    public User findByUsernameAndPassword(String username, String password) {
        String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";
        User user = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setEmail(rs.getString("email"));
                    user.setPhoneNumber(rs.getString("phonenumber"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username and password", e);
        }
        return user;
    }

    //Método para registrar usuarios en la BD
    public boolean registerUserDB(User user) { //Recoge un usuario por parámetro
        //Preparamos la consulta
        String query = "INSERT INTO USERS (username, password, email, phonenumber) VALUES (?, ?, ?, ?)";
        //Try catch para la conexión con la BD.
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) { //Preparamos la consulta
            //no añadimos user_id porque se genera automaticamente en la BD.
            //almacenamos los atributos en el objeto user.
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());

            int affectedcolumns = statement.executeUpdate();

            if (affectedcolumns > 0) {
                System.out.println("\033[32m" + "User registered: " + user.getUsername() + "\033[0m");
                return true;
            }else{
                System.out.println("Error trying to register. Try again please.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error registering user", e);
        }
        return false;
    }

    //Método para añadir usuarios a nuestro programa, posteriormente enviados a la BD con el metodo registerUserDB
    public boolean registerUserApp() {
        Scanner scanner = new Scanner(System.in);
        //Pedimos los datos al usuario que va a ser registrado
        System.out.println("Please enter the following details to register:");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();

        // Creamos un nuevo objeto User para almacenar la información proporcionada.
        User newUser = new User(username, password, email, phoneNumber);

        // Llamamos el metodo registrarUserDB para registrarlo en la BD.
        boolean registrationSuccessful = registerUserDB(newUser);

        if (registrationSuccessful) {
            System.out.println("Registration successful! You can now log in with your new account.");
        } else {
            System.out.println("Registration failed. Please try again.");
        }

        return registrationSuccessful;
    }

    public boolean modifyPassword() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your username");
        String username = sc.nextLine();
        System.out.println("Enter your password");
        String password = sc.nextLine();
        System.out.println("Enter your new password");
        String newpassword = sc.nextLine();
        System.out.println("Confirm your new password");
        String confirmpassword = sc.nextLine();

        User currentuser = findByUsernameAndPassword(username, password);

        if (currentuser != null) {
            String query = "UPDATE USERS SET password = ? WHERE user_id = ?";

            try (Connection conn = database.DatabaseConnection.getConnection()) {
                PreparedStatement statement = conn.prepareStatement(query);

                statement.setString(1, newpassword);
                statement.setInt(2, currentuser.getId());

                int affectedcolumns = statement.executeUpdate();//ejecuta la consulta
                if (affectedcolumns > 0) { //si hay alguna columna afectada:
                    System.out.println("Password changed succesfully!");
                    return true;
                } else { //sino hay columnas afectadas
                    System.out.println("Invalid data. Please try again");
                }
            } catch (SQLException e) { //si hay una excepción SQL en la ejecución del código:
                throw new RuntimeException("Error updating your credentials", e);
            }
        }else{ //si el currentuser == null:
            System.out.println("Invalid username or password"); //Este
        }
        return false;
    }
    }


