package rawdata.pfp.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.User;

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

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        int projectToJoin = Integer.parseInt(request.getParameter("projectToJoin"));
        HttpSession session = request.getSession(true);
        int check = controller.join(projectToJoin, (User)session.getAttribute("currentUser"));

        request.setAttribute("joinCheck", check);

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse");
        requestDispatcher.forward(request, response);
        //response.sendRedirect("/browse");


    } //TODO: show more information about the project?? or implement the project to string to show more details
}
