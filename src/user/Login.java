package user;


import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    //Método para autenticar al usuario. Verificamos que el usuario y contraseña coinciden en nuestra BD
    public boolean authentication(String username, String password) {
        //Consulta que dado un usuario y contraseña, devuelve los datos del usuario.
        String query = "SELECT * FROM USERS WHERE username = ? AND password = ?";
        //Try catch para la establecer conexión con la base de datos.
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, username); //Sustituimos el primer "?" de la query por la variable username
            statement.setString(2, password); //Sustituimos el segundo "?" de la query por la variable password
            try (ResultSet resultset = statement.executeQuery()) { //try catch para la gestión de excepciones.
                if (resultset.next()) {
                    System.out.println("\033[32m" + "Logged in: " + username + "\033[0m");//Texto en verde
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding user by username and password", e); //Recogemos la excepción en caso de que la consulta este vacia.
        }
        return false;
    }

}
