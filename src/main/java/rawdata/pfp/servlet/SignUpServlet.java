package rawdata.pfp.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rawdata.pfp.controller.Controller;

/**
 * Servlet for the SignUp page of PFP
 * Created by idilhanhan on 10.05.2019.
 */
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    Controller controller;

    public SignUpServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public SignUpServlet(Controller controller){
        this.controller = controller;
    }

    /**
     * DoPost method of the signup page
     * Gets the information necessary for signing up the User
     * If process is successful redirects the User to login page
     * If not successful then back to the signup page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        boolean check = controller.signup(name, pass);

        request.setAttribute("signUp", check);
        ServletContext servletContext = getServletContext();

        if (check){
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/login");
            requestDispatcher.forward(request, response);
        }
        else{
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/signup.jsp");
            requestDispatcher.forward(request, response);
        }

    }
}
