package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by idilhanhan on 19.05.2019.
 */
@WebServlet("/leave")
public class LeaveServlet extends HttpServlet {
    Controller controller;

    public LeaveServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public LeaveServlet(Controller controller){
        this.controller = controller;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int projectToLeave = Integer.parseInt(request.getParameter("projectToLeave"));
        HttpSession session = request.getSession(true);
        boolean check = controller.leave(projectToLeave, (User)session.getAttribute("currentUser"));

        //request.setAttribute("leaveCheck", check);

        //ServletContext servletContext = getServletContext();
       // RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse");
       // requestDispatcher.forward(request, response);
        response.sendRedirect("/browse");
    }
}
