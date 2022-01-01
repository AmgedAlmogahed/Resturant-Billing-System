package com.company;

import java.sql.SQLException;
import java.util.Scanner;

public class User {

    Database db;
    Scanner input;
    User() throws SQLException, ClassNotFoundException {
        db = new Database();
        input = new Scanner(System.in);
    }


    public void options(){
        System.out.println(
                """
                         _________________________________________________________
                        |                       Welcome                           |
                        |                                                         |
                        |---------------------- 1) show menu     -----------------|
                        |---------------------- 2) make an order -----------------|
                        |---------------------- 3) show cart     -----------------|
                        |---------------------- 4) clear cart    -----------------|
                        |---------------------- 5) Make payment  -----------------|
                        |---------------------- 6) exit          -----------------|
                        |                                                         |
                        |_________________________________________________________|
                        """
        );
    }

    public void start(int userId) throws SQLException, ClassNotFoundException {
        boolean bol = true;
        while(bol) {
            options();
            Scanner in = new Scanner(System.in);
            System.out.print("Enter your choice number: ");
            int choice = in.nextInt();
            switch (choice) {
                case 1 -> {
                    showMenu();
                }
                case 2 -> {
                    placeOrder(userId);
                }
                case 3 -> {
                    showCart();
                }
                case 4 -> {
                    clearCart();
                }
                case 5 ->{
                    makePayment();
                }
                case 6 -> {
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

    private void makePayment() {


    }

    private void clearCart() throws SQLException {
        db.deleteCart();
    }

    private void showCart() throws SQLException {
        db.showCart();
    }

    private void placeOrder(int userId) throws SQLException {
        showMenu();
        System.out.print("How many items you want to add: ");
        int itemsNo = input.nextInt();

        try {
            for (int a = 0 ; a < itemsNo; a ++){
                System.out.println("Add item number: " + (a + 1) );
                System.out.print("product number: ");
                int productId = input.nextInt();
                System.out.print("product quantity: ");
                int quantity = input.nextInt();

                db.insertCart(userId, productId, quantity);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return;
        }

        System.out.println("Items was added to cart successfully");


    }

    private void showMenu() throws SQLException {
        db.showMenus();
    }

}
