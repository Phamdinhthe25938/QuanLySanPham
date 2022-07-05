package Controller;

import Dao.AccountDao;
import Dao.ProductDao;
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

@WebServlet(urlPatterns = "/product")

public class AdminServlet extends HttpServlet {
    ProductDao productDao = new ProductDao();
    AccountDao accountDao = new AccountDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action==null){
            action=" ";
        }
        switch (action) {
            case "edit":
                editGet(request, response);
                break;
            default:
                request.setAttribute("products",ProductService.products);
                RequestDispatcher  requestDispatcher = request.getRequestDispatcher("/Admin.jsp");
                requestDispatcher.forward(request,response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if(action==null){
            action="";
        }
        switch (action){
            case "edit":
                editPost(request, response);
                break;
        }

    }
    public boolean check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        for (int i = 0; i< AccountService.accounts.size(); i++) {
            System.out.println(AccountService.accounts.get(i));
            if (AccountService.accounts.get(i).getUserName().equals(username)
                    && AccountService.accounts.get(i).getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void editGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id= Integer.parseInt(request.getParameter("id"));
        Product p=ProductService.products.get(ProductService.findProduct(id));
        request.setAttribute("product",p);
        request.setAttribute("id",id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("Edit.jsp");
        requestDispatcher.forward(request,response);
    }
    public void editPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        int id= Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String img = request.getParameter("img");
        double price = Double.parseDouble(request.getParameter("price"));
        try {
            productDao.updateProduct(new Product(id,name,img,price));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int vt = ProductService.findProduct(id);
        ProductService.edit(vt,new Product(id,name,img,price));
        response.sendRedirect("/product");
    }
}