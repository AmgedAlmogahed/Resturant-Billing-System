package com.company;

import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
    Database db;
    Scanner input;
    Admin() throws SQLException, ClassNotFoundException {
        db = new Database();
        input = new Scanner(System.in);
    }

    public void showOptions(){
        System.out.println(
                """
                         ________________________________________________________
                        |                    Welcome                             |
                        |                                                        |
                        |------------------- 1) Add products          -----------|
                        |------------------- 2) Update products       -----------|
                        |------------------- 3) Delete products       -----------|
                        |------------------- 4) Show all products     -----------|
                        |------------------- 5) Show purchases        -----------|
                        |------------------- 6) Add user              -----------|
                        |------------------- 7) Show users            -----------|
                        |------------------- 8) Delete user           -----------|
                        |------------------- 9) update user password  -----------|
                        |-------------------10) exit                  -----------|
                        |                                                        |
                        |________________________________________________________|
                        """
        );
    }

    public void start() throws SQLException, ClassNotFoundException {
        Scanner in = new Scanner(System.in);
        boolean bol = true;
        while(bol) {
            showOptions();
            System.out.print("Enter your choice number: ");
            int choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    addProduct();
                }
                case 2 -> {
                    updateProduct();
                }
                case 3 -> {
                    deleteProduct();
                }
                case 4 -> {
                    showAllMenuItems();
                }
                case 5 ->{
                    showPurchase();
                }
                case 6 -> {
                    addUsers();
                }
                case 7 ->{
                    showUsers();
                }
                case 8 ->{
                    deleteUser();
                }
                case 9 ->{
                    updateUserPassword();
                }
                case 10 -> {
                    bol = false;
                    Main m = new Main();
                    m.start();
                }
                default -> {
                    System.out.println("Please Enter a valid choice");
                }
            }
        }
    }

    private void updateUserPassword() throws SQLException {
        showUsers();
        System.out.print("Choose user id : ");
        int id = input.nextInt();
        System.out.print("Enter the new password: ");
        String password = input.next().trim();
        db.updateUserPassword(password, id);
    }

    private void deleteUser() throws SQLException {
        showUsers();
        System.out.print("Enter the id of the User you want to delete: ");
        int id = input.nextInt();
        db.deleteUser(id);
    }

    private void addUsers() throws SQLException {
        System.out.print("Username: ");
        String name = input.next().trim();
        System.out.print("Password: ");
        String password = input.next().trim();

        db.insertUser(name, password, Main.admin);
    }

    private void showUsers() throws SQLException {
        db.showUsers();
    }

    private void showPurchase() {
    }

    private void showAllMenuItems() throws SQLException {
        db.showMenus();
    }

    private void deleteProduct() throws SQLException {
        showAllMenuItems();
        System.out.print("Enter the item id you want to delete: ");
        int id = input.nextInt();
        db.deleteMenu(id);
    }

    private void updateProduct() throws SQLException {
        db.showMenus();
        System.out.print("Choose the item id you want to update: ");
        int itemId = input.nextInt();
        System.out.print("Entry the new quantity number: ");
        int quantity = input.nextInt();

        db.updateMenuQuantity(quantity,itemId);
    }

    private void addProduct() {
        System.out.println("How many items you want to add: ");
        int itemsNo = input.nextInt();


        try {
            for (int a = 0 ; a < itemsNo; a++){
                System.out.println("Enter the details of Item number " + (a + 1));
                System.out.print("Name: ");
                String Name = input.next().trim();
                System.out.print("Price: ");
                int price = input.nextInt();
                System.out.print("Quantity: ");
                int quantity = input.nextInt();


                db.insertMenu(Name,price,quantity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        System.out.println("menu inserted successfully");

    }


}
