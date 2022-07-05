package Controller;

import Controller.Filter.FilterLogin;
import Dao.AccountDao;
import Dao.ProductDao;
import Model.Account;
import Model.Product;
import Service.AccountService;
import Service.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    ProductDao productDao = new ProductDao();
    AccountDao accountDao = new AccountDao();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if(action==null){
            action="";
        }
        switch (action){
            case "signup":
                try {
                    signup(request, response);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                if (username.equals("admin") && password.equals("admin")) {
                    FilterLogin.account=new Account(username,password);
                    request.setAttribute("username",username);
                    request.setAttribute("products", ProductService.products);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("/Admin.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    if (check( request,response)){
                        request.setAttribute("username",username);
                        request.setAttribute("products", ProductService.products);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/User.jsp");
                        requestDispatcher.forward(request, response);
                    } else {
                        String message = "Check password again !";
                        request.setAttribute("message", message);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                        requestDispatcher.forward(request, response);
                    }
                }
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request, response);
    }

    public boolean check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        for (int i = 0; i< AccountService.accounts.size(); i++){
            System.out.println(AccountService.accounts.get(i));
            if (AccountService.accounts.get(i).getUserName().equals(username)
                    && AccountService.accounts.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        accountDao.insertAccount(new Account(username,email,password));
        RequestDispatcher  requestDispatcher = request.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request,response);
    }
}
