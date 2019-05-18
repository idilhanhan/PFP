package rawdata.pfp.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rawdata.pfp.controller.Controller;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by idilhanhan on 11.05.2019.
 */
@WebServlet("/join")
public class JoinServlet extends HttpServlet {

    Controller controller;

    public JoinServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public JoinServlet(Controller controller){
        this.controller = controller;
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

        int projectToJoin = Integer.parseInt(req.getParameter("projectToJoin"));
        controller.join(projectToJoin);

    } //TODO: show more information about the project?? or implement the project to string to show more details
}
