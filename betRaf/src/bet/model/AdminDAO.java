package bet.model;

import bet.utils.Constant;

import java.sql.*;

public class AdminDAO {

    public String regAdmin(Admin admin){
        String query = "INSERT INTO " + Constant.TABLE_ADMIN + " (username, password, email) VALUES (?, ?, ?)";

        String msg1 = "";
        try {

            Connection connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, admin.getPassword());
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.executeUpdate();
            msg1 = "admin";
        } catch (SQLException e) {
            e.printStackTrace();
            msg1 = "Failure adding new admin";
        }
        return msg1;
    }

    public String regLogin(String name, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String msg = "";
        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");

            preparedStatement = connection.prepareStatement("SELECT  * FROM  " + Constant.TABLE_ADMIN + " Where username = ? AND password = ?");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                msg = "Successful login";
            } else {
                msg = "No user found";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public Admin getUser(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        boolean isAdmin = false;
        Admin admin = null;
        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");

            preparedStatement = connection.prepareStatement("SELECT * FROM " + Constant.TABLE_ADMIN + " Where username = ?");

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username2 = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                //int id, String username, String password, String email, boolean admin
                admin = new Admin (id, username2, password, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
