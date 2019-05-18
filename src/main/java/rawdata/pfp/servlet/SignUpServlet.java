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
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {

    Controller controller;

    public SignUpServlet(Controller controller){
        this.controller = controller;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        boolean check = controller.signup(name, pass);

        if (check){
            res.sendRedirect("login.html");
        }
        else{
            res.sendRedirect("signup.html");
        }

    }
}
