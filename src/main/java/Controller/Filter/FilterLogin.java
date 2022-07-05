package Controller.Filter;

import Model.Account;
import Service.AccountService;
import Service.ProductService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = "/product")
public class FilterLogin implements Filter {
  public static Account account;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String username= request.getParameter("username");
//        String password = request.getParameter("password");
        if (account==null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
            dispatcher.forward(request,response);
        }
        else {
            chain.doFilter(request,response);
        }
    }
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
