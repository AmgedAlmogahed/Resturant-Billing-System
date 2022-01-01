package com.company;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static final String user = "USER";
    public static final String admin = "ADMIN";




    public static void mainOptions(){
        System.out.println(
                """
                         _________________________________________________________
                        |        (*^_^*)          Welcome          (*^_^*)        |
                        |                                                         |
                        |---------------------- 1) Login    ----------------------|
                        |---------------------- 2) Register ----------------------|
                        |---------------------- 3) Exit     ----------------------|
                        |                                                         |
                        |_________________________________________________________|
                        
                    """
        );
    }

    public void start() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        boolean bol = true;
        while(bol) {
            mainOptions();
            System.out.print("Enter your choice number: ");
            int choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    login();
                }
                case 2 -> {
                    register();
                }
                case 3 -> {
                    bol = false;
                    System.out.println("Thank you for coming (*^_^*)");
                }
                default -> {
                    System.out.print("Please Enter a valid choice: ");
                }
            }
        }
    }


    public void login() throws SQLException, ClassNotFoundException {
        System.out.println("""
                 _______________________________________________________
                |                                                       |
                |                       Login                           |
                |_______________________________________________________|
                """);
        Scanner in = new Scanner(System.in);
        System.out.println("Please Enter your details");
        System.out.print("Username: ");
        String userName = in.next().trim();
        System.out.print("Password: ");
        String password = in.next().trim();

        Database db = new Database();
        User user = new User();
        Admin admin = new Admin();

        HashMap<String, String> userInfo = db.validateUser(userName,password);

        if (!userInfo.isEmpty()){
            String type = userInfo.get("type");
            if(type.equals(Main.user)){
                user.start(Integer.parseInt(userInfo.get("id")));
            } else if(type.equals(Main.admin)){
                admin.start();
            }
        }else {
            System.out.println("your username or password is wrong ＞︿＜");
        }
    }

    public void register() throws SQLException, ClassNotFoundException {
        System.out.println("""
                 _______________________________________________________
                |                                                       |
                |                       Register                        |
                |_______________________________________________________|
                """);
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 0 to exit");
            System.out.println("Please Enter your details");
            System.out.print("Username: ");
            String userName = in.next().trim();
            System.out.print("Password: ");
            String password = in.next().trim();

            Database db = new Database();

            String name = db.getUserName(userName);
            if (userName.equals("0") || password.equals("0")) {
                break;
            } else if (userName.length() <= 3 || password.length() <= 5) {
                System.out.println("""
                        Username length must be greater than 3
                        Password length must be greater than 5
                        please try again
                        """);

            } else {
                if (name.isEmpty() || name.isBlank()) {
                    db.insertUser(userName, password, Main.user);
                    break;
                } else {
                    System.out.println("User Already exist, please choose another username");
                }
            }


        }


    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
       Main m = new Main();
       m.start();
    }
}
