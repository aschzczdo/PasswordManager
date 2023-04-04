
public class Main {

    public static void main(String[] args) {
        UI.LoginUI.main(args);
    }
}

        /*int opcion;
        Scanner sc = new Scanner(System.in);
        String username;
        String password;
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("\033[32m" + "Connection to the DB successfull!" + "\033[0m");
                System.out.println("Pres 1 to register");
                System.out.println("Press 2 to login");
                System.out.println("Press 3 to modify your password");
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
                    case 3:
                        user = Login.loginForm();
                        Login.loginDB(user);
                        UserDbConnection userDbConnection = new UserDbConnection();
                        userDbConnection.updatePassword(user);


                }
            } else {
                System.out.println("Failed to establish a connection to the database.");
            }
            connection.close();
        } catch (Exception e) {
            System.out.println("Error occurred while connecting to the database:");
            e.printStackTrace();
        }*/
