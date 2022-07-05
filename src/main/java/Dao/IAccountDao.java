package Dao;

import Model.Account;
import Model.Product;

import java.sql.SQLException;
import java.util.List;

public interface IAccountDao {

    void insertAccount(Account account) throws SQLException;

    Account selectAccount (int id);

    List<Account> selectAllAccounts ();

    boolean deleteAccount (int id) throws SQLException;

    boolean updateAccount (Product user) throws SQLException;
}
