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

//All function that requires user connection with DB
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
                    user.setSalt(rs.getBytes("salt"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username and password", e);
        }
        return user;
    }
    public boolean modifyPasswordDB() {
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
