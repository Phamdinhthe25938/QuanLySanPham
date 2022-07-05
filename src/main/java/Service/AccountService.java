package Service;

import Dao.AccountDao;
import Model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
      static   AccountDao accountDao = new AccountDao();
      static public ArrayList<Account> accounts = accountDao.selectAllAccounts();


       static public void add(Account account){
           accounts.add(account);
       }
       public ArrayList allAccount(){
           return (ArrayList) accounts;
       }
}
