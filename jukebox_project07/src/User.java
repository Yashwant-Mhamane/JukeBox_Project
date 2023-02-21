import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private int userid;
    private String username;
    private String password;

    public User() {
    }

    Connection connection=null;
    Statement statement;
    ResultSet rs;
    Scanner sc= new Scanner(System.in);
    int choose=0;
    static int uid1;
    int x=0;

    public Connection getConnection()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/JukeBox","root","root");
        }
        catch (SQLException | ClassNotFoundException ex)
        {
            System.out.println(ex);
        }
        return connection;
    }
    public void checkUser() throws SQLException {
        getConnection();
        statement = connection.createStatement();
        System.out.println("Enter user name :");
        String userid = sc.next();
        statement = connection.createStatement();
        rs = statement.executeQuery("select username from users;");
        ArrayList<String> str = new ArrayList<String>();
        while (rs.next()) {
            str.add(rs.getString(1));
        }
        if (str.contains(userid)) {
            System.out.println("Enter password :");
            String pass = sc.next();
            String dbpass = "";
            ResultSet rs = statement.executeQuery("select * from users where username='" + userid + "'");
            while (rs.next()) {
                uid1 = rs.getInt(1);
                dbpass = rs.getString(3);
            }
            if (dbpass.equals(pass)) {
                System.out.println("Login successful 👋\n");
            } else {
                System.out.println("Wrong Credentials\n");
                System.out.println("Choose 1 for re-enter credentials or 2 for Create a New Account :");
                choose = sc.nextInt();
                if (choose == 1) {
                    checkUser();
                }
               else if (choose == 2) {
                    newUser();
                }
            }
        }
        else
        {
            System.out.println("Wrong Username.\n");
            System.out.println("Choose 1 for re-enter username or 2 for Create a New Account :");
            choose = sc.nextInt();
            if (choose == 1) {
                checkUser();
            }
           else if (choose == 2) {
                newUser();
            }
        }
    }
    public int newUser() throws SQLException {
        getConnection();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new User Name: ");
        String username = sc.next();

        statement = connection.createStatement();
        rs = statement.executeQuery("select username from users;");
        ArrayList<String> str = new ArrayList<String>();
        while (rs.next()) {
            str.add(rs.getString(1));
        }
        //System.out.println(str);
        if (str.contains(username)) {
            System.out.println("Userid already exist take another one.");
            newUser();
        } else {
            System.out.println("Enter new password : ");
            String pass = sc.next();
            statement = connection.createStatement();
            x = statement.executeUpdate("insert into users (username, password) values('" + username + "' , '" + pass + "')");
            if (x > 0) {
                System.out.println("Account created successfully.😊\n");
                System.out.println("Please LogIn to continue.");
            } else {
                System.out.println("Sorry🫤,Error while account creation");
            }
        }
        return x;
    }
}
