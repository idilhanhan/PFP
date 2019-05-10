package rawdata.pfp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import rawdata.pfp.controller.Controller;

/**
 * Created by idilhanhan on 10.05.2019.
 */
@WebServlet("/login.html")
public class LoginServlet extends HttpServlet{

    Controller controller; //not sure about this?

    //what is inject?
    public LoginServlet(Controller controller){
        this.controller = controller;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        boolean check = controller.login(name, pass);

        if (check){
            res.sendRedirect("browse.html"); //this links the servlet!!
        }
        else{
            res.sendRedirect("index.jsp"); //maybe?
        }

    }

}
