package user;

public class User {
    private int user_id;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;

    // Constructores
    //Constructor sin parámetros
    public User(){

    }
    public User(int user_id, String username, String email, String password, String phoneNumber) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(int user_id, String username, String email, String password) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(int user_id, String username, String password) {
        this.user_id = user_id;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}