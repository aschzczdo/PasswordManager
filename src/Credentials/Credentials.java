package Credentials;

public class Credentials {
    private int cred_id;
    private int user_id;
    private String websiteUrl;
    private String websiteName;
    private String username;
    private String email;
    private String password;

    //Constructores
    //Constructor con todos los parametros
    public Credentials(int cred_id, int user_id, String websiteUrl, String websiteName, String username, String email, String password) {
        this.cred_id = cred_id;
        this.user_id = user_id;
        this.websiteUrl = websiteUrl;
        this.websiteName = websiteName;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //Constructor con todos los parámetros excepto websitename
    public Credentials(int cred_id, String websiteUrl, String username, String email, String password) {
        this.cred_id = cred_id;
        this.user_id = user_id;
        this.websiteUrl = websiteUrl;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //Constructor con todos los parámetros menos websitename y email
    public Credentials(int cred_id, int user_id, String websiteUrl, String username, String password) {
        this.cred_id = cred_id;
        this.user_id = user_id;
        this.websiteUrl = websiteUrl;
        this.username = username;
        this.password = password;
    }

    //SETTERS Y GETTERS

    public int getCred_id() {
        return cred_id;
    }

    public void setCred_id(int cred_id) {
        this.cred_id = cred_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getWebsiteName() {
        return websiteName;
    }

    public void setWebsiteName(String websiteName) {
        this.websiteName = websiteName;
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
}
