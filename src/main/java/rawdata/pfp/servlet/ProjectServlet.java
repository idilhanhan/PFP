package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;

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
import java.util.List;

/**
 * Created by idilhanhan on 20.05.2019.
 */
@WebServlet("/project")
public class ProjectServlet extends HttpServlet{

    Controller controller;

    public ProjectServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public ProjectServlet(Controller controller){
        this.controller = controller;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { //TODO: check if this works with get or post

        int projectID = Integer.parseInt(request.getParameter("projectID"));

        String nameAbstract = controller.getNameAbstract(projectID);
        request.setAttribute("nameAbstract", nameAbstract);

        List<String> participantNames = controller.getParticipants(projectID);
        request.setAttribute("participantNames", participantNames);

        String creator = controller.getCreator(projectID);
        request.setAttribute("creator", creator);

        int limit = controller.getMemberLimit(projectID);
        request.setAttribute("limit", limit);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/projectDetail.jsp");
        requestDispatcher.forward(request, response);
    }
}
