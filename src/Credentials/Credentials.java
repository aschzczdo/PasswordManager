package Credentials;

public class Credentials {
    private int cred_id;
    private int user_id;
    private String websiteurl;
    private String websitename;
    private String username;
    private String email;
    private String password;

    //Constructores
    //Constructor con todos los parametros
    public Credentials(int cred_id, int user_id, String websiteurl, String websitename, String username, String email, String password) {
        this.cred_id = cred_id;
        this.user_id = user_id;
        this.websiteurl = websiteurl;
        this.websitename = websitename;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //Constructor con todos los parámetros excepto websitename
    public Credentials(int cred_id, int user_id, String websiteurl, String username, String email, String password) {
        this.cred_id = cred_id;
        this.user_id = user_id;
        this.websiteurl = websiteurl;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    //Constructor con todos los parámetros menos websitename y email
    public Credentials(int cred_id, int user_id, String websiteurl, String username, String password) {
        this.cred_id = cred_id;
        this.user_id = user_id;
        this.websiteurl = websiteurl;
        this.username = username;
        this.password = password;
    }

}
