package com.company;

import javax.lang.model.element.Name;
import java.sql.*;
import java.util.HashMap;

public class Database {
    static String url = "jdbc:mysql://localhost:3307/billing";
    static String user = "root";
    static String pwd = "";
    Connection con;
    Statement st;


    public Database() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.con = DriverManager.getConnection(url, user, pwd);
        this.st = con.createStatement();
    }

    /**
     * Menus Queries
     */
    public void insertMenu(String productName, int productPrice, int productQuantity) throws SQLException {
        String sqlInsert = "insert into menu (`name`, `price`, `available_qty`) values('" + productName + "','" + productPrice + "','" + productQuantity + "')";
        this.st.executeUpdate(sqlInsert);
    }

    public void showMenus() throws SQLException {
        String sql = "select * from menu";
        ResultSet rs = st.executeQuery(sql);

        CommandLineTable st = new CommandLineTable();

        st.setShowVerticalLines(false);
        st.setHeaders("id", "Name", "Price", "Quantity");
        while (rs.next()) {
            st.addRow(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4));
        }
        st.print();
    }

    public void updateMenuQuantity(int quantity, int id) throws SQLException {
        String sqlupdate = "update menu set `available_qty` = '" + quantity + "' where `id` = '" + id + "'";
        st.executeUpdate(sqlupdate);
        System.out.println("Quantity updated successfully to: " + quantity);
    }

    public void deleteMenu(int id) throws SQLException {
        String sqldelete = "delete from menu where `id` ='" + id + "'";
        st.executeUpdate(sqldelete);
        System.out.println("menu deleted successfully");
    }

    /**
     * Users Queries
     */
    public void insertUser(String userName, String userPassword, String userType) throws SQLException {
        String sqlInsert = "insert into user (`username`, `password`, `type`) values('" + userName + "','" + userPassword + "','" + userType + "')";
        this.st.executeUpdate(sqlInsert);
        System.out.println("User inserted successfully");
    }

    public void showUsers() throws SQLException {
        String sql = "select * from user";
        ResultSet rs = st.executeQuery(sql);

        CommandLineTable st = new CommandLineTable();

        st.setShowVerticalLines(false);
        st.setHeaders("id", "Name", "Password", "Type");
        while (rs.next()) {
            st.addRow(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4));
        }
        st.print();
    }

    public HashMap<String, String> validateUser(String userName, String password) throws SQLException {
        String sql = "select * from user";
        ResultSet rs = st.executeQuery(sql);

        HashMap<String, String> credentials = new HashMap<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String Name = rs.getString(2);
            String pass = rs.getString(3);
            String type = rs.getString(4);
            if (Name.equals(userName) && pass.equals(password)) {
                credentials.put("name", Name);
                credentials.put("password", pass);
                credentials.put("type", type);
                credentials.put("id", String.valueOf(id));
            }

        }

        return credentials;
    }

    public String getUserName(String name) throws SQLException {
        String sql = "select * from user";
        ResultSet rs = st.executeQuery(sql);

        String Name = "";
        while (rs.next()) {

            if (Name.equals(name)) {
                Name = rs.getString(2);
                return Name;
            }

        }
        return Name;
    }

    public void updateUserPassword(String password, int id) throws SQLException {
        String sqlupdate = "update user set `password` = '" + password + "' where `id` = '" + id + "'";
        st.executeUpdate(sqlupdate);
        System.out.println("Password updated successfully");
    }

    public void deleteUser(int id) throws SQLException {
        String sqldelete = "delete from user where `id` ='" + id + "'";
        st.executeUpdate(sqldelete);
        System.out.println("User deleted successfully");
    }

    /**
     * Cart Queries
     */

    public void insertCart(int userId, int productId, int quantity) throws SQLException {

        String sqlInsert = "insert into cart (`user_id`, `product_id`, `quantity`) values('" + userId + "','" + productId + "','" + quantity + "')";
        try {
            this.st.executeUpdate(sqlInsert);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("some error occurred please try again");
        }
    }

    public void showCart() throws SQLException {
        String sql = "SELECT cart.id,menu.name, menu.price, cart.quantity " +
                "FROM cart " +
                "JOIN menu ON product_id = menu.id " +
                "WHERE cart.user_id;";

        ResultSet rs = st.executeQuery(sql);
        CommandLineTable st = new CommandLineTable();

        st.setShowVerticalLines(false);
        st.setHeaders("Item no", "Name", "Price", "Quantity");
        while (rs.next()) {
            st.addRow(String.valueOf(rs.getInt(1)), rs.getString(2), rs.getString(3), rs.getString(4));
        }
        st.print();

    }

    public HashMap<String, String> getCart() throws SQLException {
        String sql = "SELECT cart.id,menu.name, menu.price, cart.quantity " +
                "FROM cart " +
                "JOIN menu ON product_id = menu.id " +
                "WHERE cart.user_id;";
        ResultSet rs = st.executeQuery(sql);

        HashMap<String, String> cartItems = new HashMap<>();
        while (rs.next()) {
            int id = rs.getInt(1);
            String Name = rs.getString(2);
            String pass = rs.getString(3);
            String type = rs.getString(4);
            cartItems.put("id", Name);
            cartItems.put("name", pass);
            cartItems.put("price", type);
            cartItems.put("quantity", String.valueOf(id));
        }
        return cartItems;
}

    public void updateCartQuantity(int quantity, int id) throws SQLException {
        String sqlupdate = "update cart set `quantity` = '" + quantity + "' where `id` = '" + id + "'";
        st.executeUpdate(sqlupdate);
        System.out.println("Quantity updated successfully to: " + quantity);
    }

    public void deleteCart(int id) throws SQLException {
        String sqldelete = "delete from cart where `id` ='" + id + "'";
        st.executeUpdate(sqldelete);
        System.out.println("Item number" + id + "was deleted successfully");
    }

    public void deleteCart() throws SQLException {
        String sqldelete = "delete from cart";
        st.executeUpdate(sqldelete);
        System.out.println("Cart Items was deleted successfully");
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Database db = new Database();

        db.showMenus();
        db.showUsers();
        db.showCart();

    }

}
