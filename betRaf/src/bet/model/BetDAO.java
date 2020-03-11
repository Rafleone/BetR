package bet.model;

import java.sql.*;

public class BetDAO {

    final static String URL = "jdbc:mysql://localhost:3306/dbbet";
    public String add(Bet bet){
        String query = "insert into bet (name , age, country, bet, event, game, payment)" +
                "values (?,?,?,?,?,?,?)";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bet.getName());
            preparedStatement.setInt(2, bet.getAge());
            preparedStatement.setString(3, bet.getCountry());
            preparedStatement.setInt(4, bet.getBet());
            preparedStatement.setString(5, bet.getEvent());
            preparedStatement.setString(6, bet.getGame());
            preparedStatement.setString(7, bet.getPayment());
            preparedStatement.executeUpdate();

            return "Successfully created new entry";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure creating new entry";
        }
    }

    public ResultSet searchByTeamName(String name){
        String query2 = "";
        if (name.equals("")) {//User didnt entered any team_name - displaying all entries
            query2 = "SELECT * FROM bet";
        } else {// User entered team_name displaying specific entries
            query2 = "SELECT * FROM bet WHERE name LIKE '" + name + "'";
        }

        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, "root", "");
            preparedStatement = connection.prepareStatement(query2);
            resultSet = preparedStatement.executeQuery(query2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void editById(Bet bet){
        String query = "update bet set name =?, age=?, country=?, bet=?, event=?, game=?, payment=? " +
                " where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bet.getName());
            preparedStatement.setInt(2, bet.getAge());
            preparedStatement.setString(3, bet.getCountry());
            preparedStatement.setInt(4, bet.getBet());
            preparedStatement.setString(5, bet.getEvent());
            preparedStatement.setString(6, bet.getGame());
            preparedStatement.setString(7, bet.getPayment());
            preparedStatement.setInt(8, bet.getId());
            preparedStatement.executeUpdate();

            System.out.println("Pavyko paredaguoti esama irasa");
        } catch (SQLException e) {
            System.out.println("Ivyko klaida redaguojant esama irasa");
            e.printStackTrace();
        }
    }
    public void deleteById(int id){
        String query = "delete from bet where id=?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            System.out.println("Pavyko istrinti esama irasa");
        } catch (SQLException e) {
            System.out.println("Ivyko klaida istrinant esama irasa");
            e.printStackTrace();
        }
    }
}
