package Dao;

import Model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IProductDao {
    void insertProduct (Product product) throws SQLException;

     Product selectProduct (int id);

    List<Product> selectAllProducts ();

    boolean deleteProduct (int id) throws SQLException;

   boolean updateProduct (Product user) throws SQLException;
}
