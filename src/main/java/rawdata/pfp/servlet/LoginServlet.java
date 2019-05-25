package rawdata.pfp.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.User;


/**
 * Servlet for the Login page of PFP
 * Created by idilhanhan on 10.05.2019.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet{

    private Controller controller; //not sure about this?

    public LoginServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public LoginServlet(Controller controller){
        this.controller = controller;
    }

    /**
     * DoPost method of the login page
     * Gets the information given by the User and does the login operation
     * If process is successful redirects the User to Home page
     * If not successful then to the loginError page
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        User curr = controller.login(name, pass);

        if (curr != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("currentUser", curr);
            response.sendRedirect("browse");
        } else {
            response.sendRedirect("loginError.jsp"); //create a popup to say that the user has failed
        }

    }

}
