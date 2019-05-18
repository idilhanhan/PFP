package rawdata.pfp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletConfig;



import java.io.IOException;
import java.sql.SQLException;

import rawdata.pfp.controller.Controller;


/**
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
    } //what can ı do with this?? check inject*/

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        System.out.println("Inside login servlet" + name);
        boolean check = controller.login(name, pass);

        if (check){
            res.sendRedirect("browse.html"); //this links the servlet!!
        }
        else{
            res.sendRedirect("index.jsp"); //maybe?
        }

    }

}
