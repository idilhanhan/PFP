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
import javax.servlet.http.HttpSession;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.User;

/**
 * Servlet that handles the Leave feature of PFP
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

    /**
     * DoPost method of the Leave feature
     * Redirects the user to Home page of PFP after taking the User out from the project they chose to leave
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int projectToLeave = Integer.parseInt(request.getParameter("projectToLeave"));
        HttpSession session = request.getSession(true);
        controller.leave(projectToLeave, (User)session.getAttribute("currentUser"));

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse");
        requestDispatcher.forward(request, response);
    }
}
