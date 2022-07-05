package Dao;

import Connect.ConnectAccountMySql;
import Model.Account;
import Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements IAccountDao{
    private static final String INSERT_ACCOUNT_SQL = "INSERT INTO account (userName,email,password) VALUES (?, ?, ?);";
    private static final String SELECT_ALL_ACCOUNT ="SELECT * FROM account";
    ConnectAccountMySql connectAccountMySql = new ConnectAccountMySql();

//    Account account = new Account();
    @Override
    public void insertAccount(Account account) throws SQLException {
        try (Connection connection = connectAccountMySql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ACCOUNT_SQL)) {
            preparedStatement.setString(1,account.getUserName());
            preparedStatement.setString(2, account.getEmail());
            preparedStatement.setString(3,account.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Account selectAccount(int id) {
        return null;
    }

    @Override
    public ArrayList<Account> selectAllAccounts() {

        ArrayList<Account> accounts = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = connectAccountMySql.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ACCOUNT);){
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("userName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                accounts.add(new Account(id, username, email, password,status));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return accounts;
    }

    @Override
    public boolean deleteAccount(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean updateAccount(Product user) throws SQLException {
        return false;
    }
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
