package Dao;

import Connect.ConnectProductMySql;
import Model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements IProductDao {
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (id,name,imgLink,price) VALUES (?, ?, ?, ?);";
    private static final String SELECT_PRODUCT_BY_ID = "select id,name,imgLink,price from product where id =?";
    private static final String SELECT_ALL_PRODUCT = "select * from product";
    private static final String DELETE_PRODUCT_SQL = "delete from product where id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "update product set name = ?,imgLink= ?, price =? where id = ?;";
    ConnectProductMySql connectMySql = new ConnectProductMySql();
    @Override
    public void insertProduct (Product product) throws SQLException {
        try (Connection connection = connectMySql.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setInt(1,product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setString(3, product.getLinkImg());
            preparedStatement.setDouble(4,product.getPrice());
            preparedStatement.execute();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Product selectProduct (int id) {
        Product product = null;
        // Step 1: Establishing a Connection
        try (Connection connection = connectMySql.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);) {
            preparedStatement.setInt(1, id);

            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String imgLink = rs.getString("imgLink");
                double price = rs.getInt("price");
                product = new Product(id, name,imgLink, price);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return product;
    }
    @Override
    public List<Product> selectAllProducts() {
        List<Product> products = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = connectMySql.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCT)){
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();
            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String imgLink = rs.getString("imgLink");
                double price = rs.getDouble("price");
                products.add(new Product(id, name, imgLink, price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return products;
    }
    @Override
    public boolean deleteProduct (int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = connectMySql.getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    @Override
    public boolean updateProduct(Product product) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = connectMySql.getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_SQL);) {
            statement.setInt(4, product.getId());
            statement.setString(1, product.getName());
            statement.setString(2, product.getLinkImg());
            statement.setDouble(3, product.getPrice());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
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
