package user;

import AES.SecretKey;

import java.nio.charset.StandardCharsets;

//Clase que almacena todos los datos de cada usuario.
public class User {
    //Atributos de la clase User
    private int user_id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private byte[] salt;

    // Constructores
    //Constructor sin parámetros
    public User (){

    }
    public User(int user_id , String username, String password, String email, String phoneNumber,byte[] salt) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.salt = salt;
    }
    public User(String username, String password, String email, String phoneNumber,byte[] salt) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.salt = salt;
    }
    public User (String username, String password){
        this.username = username;
        this.password = password;
    }

    //Setter y getters:
    public int getId() {

        return user_id;
    }

    public void setId(int id) {

        this.user_id = id;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }
}